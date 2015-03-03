package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Praetorian on 03-03-2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public SQLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Er måske for åbent hvis man kan tilgå SQLiteHelper udefra
    public void executeSQL(String sql)
    {
        db.execSQL(sql);
    }
}
