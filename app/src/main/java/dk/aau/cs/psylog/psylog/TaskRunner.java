package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;
import dk.aau.cs.psylog.data_access_layer.generated.Task;

public class TaskRunner extends Service {
    private ArrayList<ModuleTask> tasks;

    public TaskRunner() {
        this.tasks = new ArrayList<>();
    }

    public void add(ModuleTask task){
        this.tasks.add(task);
    }

    private void sortAfterRunningTime(){
        Collections.sort(this.tasks, new TaskComparator());
    }

    private void updateTaskTime(ModuleTask task)
    {
        Module m = task.getModule();
        if (m instanceof SensorModule) {
            task.setTime(getNextTime(((SensorModule) m).getTask()));
        }
        else if (m instanceof AnalysisModule) {
            task.setTime(getNextTime(((SensorModule) m).getTask()));
        }
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

    private void startTask(ModuleTask task){
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
