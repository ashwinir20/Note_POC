package com.example.ashwinirajagopal.locationnote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.GridView;
import android.app.LoaderManager;
import android.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.CursorAdapter;
import android.content.CursorLoader;
import android.content.Loader;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>

{

    private CursorAdapter cursorAdapter;

    public static final int EDITOR_REQUEST_CODE=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for(int i=0;i<4;i++) {

            insertNote("Note"+i);
        }

        Cursor cursor = getContentResolver().query(NotesContentProvider.CONTENT_URI,
                DBHelper.COLUMNS,null,null,null,null);

        String[] text = {DBHelper.NOTE_TEXT};
        int[] columns = {android.R.id.text1};

        cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,text,columns,0);

        GridView gridView = (GridView)findViewById(R.id.gridview);

        gridView.setAdapter(cursorAdapter);

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


    public void openEditor(View view){

        Intent intent = new Intent(this,EditorActivity.class);

        startActivityForResult(intent,EDITOR_REQUEST_CODE);

    }


    @Override
    public void onActivityResult(int reqCode,int resCode, Intent data){

        if(reqCode == EDITOR_REQUEST_CODE && resCode == RESULT_OK){
            restartLoader();

        }
    }

    private void restartLoader() {

        getLoaderManager().restartLoader(0, null, this);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotesContentProvider.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
