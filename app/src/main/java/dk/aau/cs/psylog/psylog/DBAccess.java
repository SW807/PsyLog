package dk.aau.cs.psylog.psylog;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class DBAccess extends ContentProvider{

    SQLiteHelper sqLiteHelper;

    @Override
    public boolean onCreate() {
        sqLiteHelper = new SQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return sqLiteHelper.readFromDB(uri.getPath().replaceFirst("/",""),projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqLiteHelper.insertToDB(uri.getPath().replaceFirst("/", ""),null,values);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
