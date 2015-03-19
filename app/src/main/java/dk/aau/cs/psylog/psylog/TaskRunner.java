package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.generated.Module;

public class TaskRunner extends Service {
    private ArrayList<Task> tasks;

    public TaskRunner() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task){
        this.tasks.add(task);
    }

    private void sortAfterRunningTime(){
        Collections.sort(this.tasks, new TaskComparator());
    }

    private void updateTaskTime(Task task)
    {
        //task.setTime(task.getModule().);
        sortAfterRunningTime();
    }

    private void startTask(Task task){
        ServiceHelper.startService(ServiceHelper.getProcessName(task.getModule()), this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TaskComparator implements Comparator<Task>{
        @Override
        public int compare(Task lhs, Task rhs) {
            return lhs.getTime().compareTo(rhs.getTime());
        }
    }
}
