package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Praetorian on 03-03-2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String primary_key = "_id";

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public static final String DUMMY_COLUMN = "sometext";
    public static final String DUMMY_TABLE = "dummytable";
    public static final String DUMMY_SQL = "create table" + DUMMY_TABLE + "(" + primary_key + " integer primary key autoincrement, " +
                                                                                 DUMMY_COLUMN + " text not null=;";
    public SQLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        this.db.execSQL(DUMMY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DUMMY_TABLE);
        onCreate(db);
    }

    //Er måske for åbent hvis man kan tilgå SQLiteHelper udefra
    public void executeSQL(String sql)
    {
        db.execSQL(sql);
    }
}
