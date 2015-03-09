package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.ArrayList;

import dk.aau.cs.psylog.generated.Module;


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
        try {
            moduleHelper.updateAllModules(modules);
        }
        catch (SQLDataException e) {
            Log.e("DB", e.getMessage());
        }
    }
}
