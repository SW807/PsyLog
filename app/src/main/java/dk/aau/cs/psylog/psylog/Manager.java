package dk.aau.cs.psylog.psylog;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.psylog.generated.Column;
import dk.aau.cs.psylog.generated.Module;
import dk.aau.cs.psylog.generated.Table;

public class Manager {

    private Context context;
    private SQLiteHelper sqLiteHelper;
    public Manager(Context context)
    {
        this.context = context;
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    private void createModuleVersionTable()
    {
        sqLiteHelper.createTable("Manager_ModuleVersions", new String[]{"Name", "Version"});
    }

    private void updateModuleVersions(String name, String version)
    {
        
    }

    private boolean newVersion(Module module)
    {

        return true;
    }

    private void createTables(Module module)
    {
        for(Table t : module.getTables())
        {
            List<String> l = new ArrayList<>();
            for (Column c : t.getColumns())
                l.add(c.getName() + " " + c.getDataType());
            sqLiteHelper.createTable(t.getName(), l.toArray(new String[l.size()]));
        }
    }
}
