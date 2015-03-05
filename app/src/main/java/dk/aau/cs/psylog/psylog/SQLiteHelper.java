package dk.aau.cs.psylog.psylog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;

    public SQLiteHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        writableDB = getWritableDatabase();
        readableDB = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }

    public Cursor readFromDB(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        return readableDB.query(table, columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }

    public long insertToDB(String table, String nullColumnHack, ContentValues values){
        return writableDB.insert(table,nullColumnHack,values);
    }

    public void createTable(String tableName, String[] columns){
        String query = "CREATE TABLE " + tableName + "( _id integer primary key autoincrement ";
        for(String s : columns){
            query += ", " + s;
        }
        query += ");";
        writableDB.execSQL(query);
    }
}
