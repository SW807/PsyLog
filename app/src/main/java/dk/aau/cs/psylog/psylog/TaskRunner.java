package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;

import static dk.aau.cs.psylog.psylog.Task.*;

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
        Module m = task.getModule();
        dk.aau.cs.psylog.data_access_layer.generated.Task.Type taskType;
        String taskValue;
        if (m instanceof SensorModule) {
            taskType = ((SensorModule) m).getTask().getType();
            taskValue = ((SensorModule) m).getTask().getValue();
        }
        else if (m instanceof AnalysisModule) {
            taskType = ((AnalysisModule) m).getTask().getType();
            taskValue = ((AnalysisModule) m).getTask().getValue();
        }

        Date newTime;

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
