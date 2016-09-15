package com.example.ashwinirajagopal.locationnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashwinirajagopal on 9/14/16.
 */
public class DBHelper extends SQLiteOpenHelper {


    //Constants for db name and version
    private static final String DATABASE_NAME = "locationote.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and columns
    public static final String TABLE_LOCATION_NOTE = "LocationNote";
    //must start with _ for primary key, since exposed to content provider
    public static final String ID = "_id";
    public static final String NOTE_TEXT = "noteText";
    //date time value, since SQLite doesnt support this data ype
    public static final String NOTE_CREATED = "noteCreated";

    //
    public static final String[] COLUMNS = {ID,NOTE_TEXT,NOTE_CREATED};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_LOCATION_NOTE + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_LOCATION_NOTE);
        onCreate(db);

    }
}
