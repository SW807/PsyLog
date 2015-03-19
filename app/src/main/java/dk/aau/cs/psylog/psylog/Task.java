package dk.aau.cs.psylog.psylog;

import java.util.Date;

import dk.aau.cs.psylog.data_access_layer.generated.Module;

public class Task {
    private Date time;
    private Module module;

    public Task(Date time, Module module){
        this.time = time;
        this.module = module;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time = time;
    }

    public Module getModule(){
        return  this.module;
    }
}
