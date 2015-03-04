package dk.aau.cs.psylog.psylog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    public static final String DUMMY_COLUMN = "sometext";
    public static final String DUMMY_TABLE = "dummytable";
    public static final String DUMMY_SQL = "create table " + DUMMY_TABLE + " (" + primary_key + " integer primary key autoincrement, " +
                                                                                 DUMMY_COLUMN + " text not null);";
    public SQLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        writableDB = getWritableDatabase();
        readableDB = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DUMMY_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DUMMY_TABLE);
        onCreate(db);
    }

    public Cursor readFromDB(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return readableDB.query(table, columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertToDB(String table, String nullColumnHack, ContentValues values){
        return writableDB.insert(table,nullColumnHack,values);
    }
}
