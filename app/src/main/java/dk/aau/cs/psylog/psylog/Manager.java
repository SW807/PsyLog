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

    private void updateModuleVersion(String name, String version) throws SQLDataException
    {
        Cursor cursor = sqLiteHelper.readFromDB(context.getString(R.string.Manager_ModuleVersionsTable), null, context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " = ?", new String[]{name}, null, null, null, null);
        if (cursor.getCount() == 1)
        {
            ContentValues cv = new ContentValues();
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn),version);
            int affected = sqLiteHelper.updateDB(context.getString(R.string.Manager_ModuleVersionsTable), cv, context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " = ?", new String[]{name});
            if(affected != 1)
                throw new InternalError("Duplicate module in modules table - RIP Project");
        }
        else if (cursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_NameColumn),name);
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn),version);
            long id = sqLiteHelper.insertToDB(context.getString(R.string.Manager_ModuleVersionsTable),cv);
            if(id == -1)
                throw new SQLDataException("Row could not be inserted");
        }
        else
            throw new InternalError("Duplicate module in modules table - RIP Project");
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
