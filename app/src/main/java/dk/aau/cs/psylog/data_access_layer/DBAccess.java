package dk.aau.cs.psylog.data_access_layer;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import dk.aau.cs.psylog.PsyLogConstants;

public class DBAccess extends ContentProvider{

    SQLiteHelper sqLiteHelper;

    @Override
    public boolean onCreate() {
        sqLiteHelper = new SQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return sqLiteHelper.readFromDB(tableFromUri(uri),projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = sqLiteHelper.insertToDB(tableFromUri(uri),values);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String whereclause, String[] whereArgs) {
        return sqLiteHelper.updateDB(tableFromUri(uri), values, whereclause, whereArgs);
    }

    private String tableFromUri(Uri uri) {
        return PsyLogConstants.SQLITE_PACKAGE_NAME + uri.getPath().replaceFirst("/","");
    }
}
