package dk.aau.cs.psylog.psylog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.SQLDataException;
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
        sqLiteHelper.createTable(context.getString(R.string.Manager_ModuleVersionsTable), new String[]{context.getString(R.string.Manager_ModuleVersionsTable_NameColumn), context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn)});
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
