package dk.aau.cs.psylog.data_access_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Column;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;
import dk.aau.cs.psylog.data_access_layer.generated.Table;
import dk.aau.cs.psylog.psylog.R;

public class ModuleHelper {
    private Context context;
    private SQLiteHelper sqLiteHelper;

    public ModuleHelper(Context context) {
        this.context = context;
        this.sqLiteHelper = new SQLiteHelper(context);
    }

    public void createModuleVersionTable() {
        sqLiteHelper.createTable(context.getString(R.string.Manager_ModuleVersionsTable), new String[]{context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " " + context.getString(R.string.SQLITE_Type_Text), context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn)+ " " + context.getString(R.string.SQLITE_Type_Real)}, false);
    }

    private void updateModuleVersion(String name, double version) throws SQLDataException {
        Cursor cursor = sqLiteHelper.readFromDB(context.getString(R.string.Manager_ModuleVersionsTable), null, context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " = ?", new String[]{name}, null, null, null, null);
        if (cursor.getCount() == 1) {
            ContentValues cv = new ContentValues();
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn), version);
            int affected = sqLiteHelper.updateDB(context.getString(R.string.Manager_ModuleVersionsTable), cv, context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " = ?", new String[]{name});
            if (affected != 1)
                throw new InternalError("Duplicate module in modules table - RIP Project");
        } else if (cursor.getCount() == 0) {
            ContentValues cv = new ContentValues();
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_NameColumn), name);
            cv.put(context.getString(R.string.Manager_ModuleVersionsTable_VersionColumn), version);
            long id = sqLiteHelper.insertToDB(context.getString(R.string.Manager_ModuleVersionsTable), cv);
            if (id == -1)
                throw new SQLDataException("Row could not be inserted");
        } else
            throw new InternalError("Duplicate module in modules table - RIP Project");
    }

    private boolean sameVersion(String name, double newVersion) {
        double version = 0;
        Cursor cursor = sqLiteHelper.readFromDB(context.getString(R.string.Manager_ModuleVersionsTable), null, context.getString(R.string.Manager_ModuleVersionsTable_NameColumn) + " = ?", new String[]{name}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1)
            version = cursor.getDouble(2);
        return version == newVersion;
    }

    public HashMap<Module, Boolean> updateAllModules(ArrayList<Module> modules) {
        HashMap<Module, Boolean> resultHashMap = new HashMap<>();
        for (Module module : modules) {
            if (!sameVersion(module.getName(), module.get_version())) {
                try {
                    updateModuleVersion(module.getName(), module.get_version());
                    resultHashMap.put(module, true);
                } catch (SQLDataException e) {
                    resultHashMap.put(module, false);
                }
                createTables(module);
            }
        }
        return resultHashMap;
    }

    private void createTables(Module module) {
        Set<Table> tables;
        if (module instanceof AnalysisModule)
            tables = ((AnalysisModule) module).getTables();
        else if (module instanceof SensorModule)
            tables = ((SensorModule) module).getTables();
        else
            return;

        for (Table t : tables) {
            List<String> l = new ArrayList<>();
            for (Column c : t.getColumns())
                l.add(c.getName() + " " + c.getDataType());
            sqLiteHelper.createTableWithTime(PsyLogConstants.SQLITE_PACKAGE_NAME + module.getName().toUpperCase() + "_" + t.getName(), l.toArray(new String[l.size()]), true);
        }
    }
}
