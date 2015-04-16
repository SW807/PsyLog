package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.generated.DataModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.Task;

public class TaskRunner extends Service {
    private ArrayList<ModuleTask> tasks = new ArrayList<>();
    Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        loadSettings();
    }

    private void loadSettings(){
        Set<String> settings = new SettingsHelper.Tasks(this).getSettings();
        if(settings != null) {
            tasks.clear();
            ArrayList<DataModule> modules = new ArrayList<>();

            for (Module module : new JSONParser(this).parse())
                if (module instanceof DataModule && ((DataModule) module).getTask() != null && new SettingsHelper.Modules(this).getSettings(module.getName()))
                    modules.add((DataModule) module);


            for (String s : settings) {
                ModuleTask m = ModuleTask.deserialize(s, modules);
                if(m != null)
                    tasks.add(m);
            }
        }
    }

    private void saveSettings(){
        Set<String> serializedTasks = new HashSet<>();
        for(ModuleTask moduleTask : tasks)
            serializedTasks.add(moduleTask.serialize());

        new SettingsHelper.Tasks(this).setSettings(serializedTasks);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        thread = new Thread(new RunTask(this));

        ArrayList<DataModule> modules = new ArrayList<>();

        HashSet<String> alreadyRunning = new HashSet<>();
        for(ModuleTask moduleTask : tasks){
            alreadyRunning.add(moduleTask.getModule().getName());
        }
        HashSet<String> shouldRun = new HashSet<>();
        for(Module module : new JSONParser(this).parse()){
            if(module instanceof DataModule && ((DataModule) module).getTask() != null && new SettingsHelper.Modules(this).getSettings(module.getName())) {
                shouldRun.add(module.getName());
                modules.add((DataModule)module);
            }
        }

        HashSet<String> remove = new HashSet<>(alreadyRunning);
        remove.removeAll(shouldRun);

        HashSet<String> add = new HashSet<>(shouldRun);
        add.removeAll(alreadyRunning);

        for(String s : remove){
            for(int i = 0; i < tasks.size(); i++){
                if(tasks.get(i).getModule().getName().equals(s)) {
                    tasks.remove(i);
                    break;
                }
            }
        }

        for(String s : add){
            for(int i = 0; i < modules.size(); i++){
                if(modules.get(i).getName().equals(s))
                    tasks.add(new ModuleTask(modules.get(i)));
            }
        }

        sortAfterRunningTime();
        saveSettings();
        if (tasks.size() > 0)
            thread.start();
        return START_STICKY;
    }

    private void add(ModuleTask task){
        this.tasks.add(task);
        sortAfterRunningTime();
        saveSettings();
    }

    private void sortAfterRunningTime(){
        Collections.sort(this.tasks, new TaskComparator());
    }

    private void updateTaskTime(ModuleTask task)
    {
        task.setNextTime();
        sortAfterRunningTime();
        saveSettings();
    }

    private class RunTask implements Runnable {

        Context ctx;
        private RunTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            while (true) {
                ModuleTask task = tasks.get(0);
                sleep();
                ServiceHelper.startService(ServiceHelper.getProcessName(task.getModule()), ctx);
                updateTaskTime(task);
            }
        }

        private void sleep(){
            long diff = tasks.get(0).getTime() - new Date().getTime();
            if (diff > 0)
                SystemClock.sleep(diff);
        }
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
