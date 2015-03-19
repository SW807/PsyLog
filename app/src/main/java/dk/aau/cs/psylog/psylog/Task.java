package dk.aau.cs.psylog.psylog;

import java.util.Comparator;
import java.util.Date;

public class Task {
    private Date time;

    public Task(Date time){
        this.time = time;
    }

    public Date getTime(){
        return time;
    }
}
