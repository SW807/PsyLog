package dk.aau.cs.psylog.psylog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Collections;

public class TaskRunner extends Service {
    private ArrayList<Task> tasks;

    public TaskRunner() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task){
        this.tasks.add(task);
    }

    private void sortAfterRunningTime(){
        Collections.sort(this.tasks, ;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
