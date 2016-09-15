package com.example.ashwinirajagopal.locationnote;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.CursorAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        insertNote("New Note");


        Cursor cursor = getContentResolver().query(NotesContentProvider.CONTENT_URI,
                DBHelper.COLUMNS,null,null,null,null);

        String[] text = {DBHelper.NOTE_TEXT};
        int[] columns = {android.R.id.text1};

        CursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,text,columns,0);

        ListView list = (ListView)findViewById(android.R.id.list);

        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void insertNote(String text){

        //ContentValues is essentially a set of key-value pairs,
        // key represents the column for the table, value is the value to be inserted in that column.
        ContentValues values = new ContentValues();
        values.put(DBHelper.NOTE_TEXT,text);

        //ContentResolver - android uses to resolve Uris to ContentProvider, here it is NotesContentProvider

        Uri noteUri = getContentResolver().insert(NotesContentProvider.CONTENT_URI,values);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
