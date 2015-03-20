package dk.aau.cs.psylog.psylog;

import java.util.Date;

import dk.aau.cs.psylog.data_access_layer.generated.DataModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;

public class ModuleTask {
    private Date time;
    private DataModule module;

    public ModuleTask(DataModule module){
        this.module = module;
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time = time;
    }

    public DataModule getModule(){
        return  this.module;
    }
}
