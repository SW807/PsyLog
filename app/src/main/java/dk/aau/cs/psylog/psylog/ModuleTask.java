package dk.aau.cs.psylog.psylog;

import android.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.aau.cs.psylog.data_access_layer.generated.DataModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.Task;

public class ModuleTask {
    private Date time;
    private DataModule module;

    public ModuleTask(DataModule module){
        this.module = module;
        setNextTime();
    }

    public Long getTime(){
        return time.getTime();
    }

    public void setTime(Date time){
        this.time = time;
    }

    public DataModule getModule(){
        return  this.module;
    }

    public void setNextTime(){
        Task task = module.getTask();
        if (task.getType() == Task.Type.INTERVAL) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, parseInterval(task.getValue()));
            time = calendar.getTime();
        }
        else if (task.getType() == Task.Type.SCHEDULED){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Pair<Integer,Integer> scheduledTime = parseScheduled(task.getValue());
            calendar.set(Calendar.HOUR_OF_DAY, scheduledTime.first);
            calendar.set(Calendar.MINUTE, scheduledTime.second);
            calendar.set(Calendar.SECOND, 0);
            if (calendar.getTimeInMillis() < new Date().getTime())
                calendar.add(Calendar.HOUR_OF_DAY, 24);
            time = calendar.getTime();
        }
    }

    private int parseInterval(String minutes){
        return Integer.parseInt(minutes);
    }

    private Pair<Integer,Integer> parseScheduled(String date) throws IllegalArgumentException
    {
        Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()){
            String[] time = date.split(":");
            return new Pair<>(Integer.parseInt(time[0]), Integer.parseInt(time[1]));
        }
        throw new IllegalArgumentException("Illegal time format of: "  + date + ". Should be HH:mm.");
    }
}
