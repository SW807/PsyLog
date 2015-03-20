package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.generated.DataModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.Task;

public class TaskRunner extends Service {
    private ArrayList<ModuleTask> tasks;

    private void initializeTasks(ArrayList<Module> modules){
        for (Module m : modules) {
            if (m instanceof DataModule) {
                 DataModule dataModule = ((DataModule) m);
                if (dataModule.getTask().getType() != Task.Type.CONTINUOUS) {
                    ModuleTask mt = new ModuleTask(dataModule);
                    mt.setTime(getNextTime(mt.getModule().getTask()));
                    tasks.add(mt);
                }
            }
        }
        sortAfterRunningTime();
    }

    @Override
    public void onCreate() {
        this.tasks = new ArrayList<>();
        JSONParser jsonParser = new JSONParser(this);
        initializeTasks(jsonParser.parse());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTask();
        return START_STICKY;
    }

    private void add(ModuleTask task){
        this.tasks.add(task);
        sortAfterRunningTime();
    }

    private void sleep(){
        long diff = new Date().getTime() - tasks.get(0).getTime().getTime();
        if (diff > 0)
            SystemClock.sleep(diff);
    }

    private void sortAfterRunningTime(){
        Collections.sort(this.tasks, new TaskComparator());
    }

    private void updateTaskTime(ModuleTask task)
    {
        task.setTime(getNextTime(task.getModule().getTask()));
        sortAfterRunningTime();
    }

    private Date getNextTime(Task task) throws IllegalArgumentException {
        if (task.getType() == Task.Type.INTERVAL) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, parseInterval(task.getValue()));
            return calendar.getTime();
        }
        else if (task.getType() == Task.Type.SCHEDULED){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parseScheduled(task.getValue()));
            calendar.add(Calendar.HOUR, 24);
            return calendar.getTime();
        }
        throw new IllegalArgumentException("Task should only be of type " + Task.Type.SCHEDULED + " and " + Task.Type.INTERVAL + ".");
    }

    private int parseInterval(String minutes){
        return Integer.parseInt(minutes);
    }

    private Date parseScheduled(String date) throws IllegalArgumentException
    {
        Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                return simpleDateFormat.parse(date);
            } catch (ParseException e) {
                Log.d("TaskRunner", e.getMessage());
            }
        }
        throw new IllegalArgumentException("Illegal time format of: "  + date + ". Should be HH:mm.");
    }

    private void startTask(){
        sleep();
        ModuleTask task = tasks.get(0);
        ServiceHelper.startService(ServiceHelper.getProcessName(task.getModule()), this);
        updateTaskTime(task);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TaskComparator implements Comparator<ModuleTask>{
        @Override
        public int compare(ModuleTask lhs, ModuleTask rhs) {
            return lhs.getTime().compareTo(rhs.getTime());
        }
    }
}
