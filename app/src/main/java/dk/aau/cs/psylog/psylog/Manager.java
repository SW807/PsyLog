package dk.aau.cs.psylog.psylog;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.ModuleHelper;
import dk.aau.cs.psylog.data_access_layer.generated.Module;


public class Manager {
    private JSONParser jsonParser;
    private ModuleHelper moduleHelper;

    public Manager(Context context)
    {
        this.jsonParser = new JSONParser(context);
        this.moduleHelper = new ModuleHelper(context);
        moduleHelper.createModuleVersionTable();
    }

    public void updateModules()
    {
        ArrayList<Module> modules =  jsonParser.parse();
        HashMap<Module, Boolean> modulesUpdateStatus = moduleHelper.updateAllModules(modules);
    }
}
