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


}
