package com.example.ashwinirajagopal.locationnote;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ashwinirajagopal on 9/14/16.
 */
public class NotesContentProvider extends ContentProvider {


    private static final String AUTHORITY = "com.example.ashwinirajagopal.locationote";
    private static final String BASE_PATH = "locationote";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    public static final String CONTENT = "Note";


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
        uriMatcher.addURI(AUTHORITY,BASE_PATH+"/#",NOTES_ID);

    }

    private SQLiteDatabase database;


    @Override
    public boolean onCreate() {

        DBHelper helper = new DBHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {


        if(uriMatcher.match(uri) == NOTES_ID){

            selection= DBHelper.ID+ "=" +uri.getLastPathSegment();
        }

        return  database.query(DBHelper.TABLE_LOCATION_NOTE,DBHelper.COLUMNS,selection,null,null,null,
                            DBHelper.NOTE_CREATED + " desc");

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long id = database.insert(DBHelper.TABLE_LOCATION_NOTE,null,values);
        return  Uri.parse(BASE_PATH+"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

            return database.delete(DBHelper.TABLE_LOCATION_NOTE,selection,selectionArgs);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return database.update(DBHelper.TABLE_LOCATION_NOTE,values,selection,selectionArgs);
    }
}
