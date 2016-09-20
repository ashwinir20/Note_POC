package com.example.ashwinirajagopal.locationnote;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {


    //To determine the action - inserting or updating a note
    private String action;

    private EditText editor;

    private String noteFilter;

    private String existingText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editor = (EditText) findViewById(R.id.editNote);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(NotesContentProvider.CONTENT);

        //if the add new note button is pressed URI is null
        if (uri == null) {

            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));

        }else{

            action = Intent.ACTION_EDIT;

            noteFilter = DBHelper.ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,DBHelper.COLUMNS,noteFilter,null,null);

            cursor.moveToFirst();
            existingText = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_TEXT));

            editor.setText(existingText);

            editor.requestFocus();

        }

    }

    public void insertNote(String text){

        ContentValues values = new ContentValues();
        values.put(DBHelper.NOTE_TEXT,text);

        //ContentResolver - android uses to resolve Uris to ContentProvider, here it is NotesContentProvider

        getContentResolver().insert(NotesContentProvider.CONTENT_URI, values);

        setResult(RESULT_OK);

    }

    public void updateNote(String newText){

        ContentValues values = new ContentValues();

        //packaging
        values.put(DBHelper.NOTE_TEXT,newText);

        //update in DB
        getContentResolver().update(NotesContentProvider.CONTENT_URI, values,noteFilter,null);

        Toast.makeText(this,"Note Updated!",Toast.LENGTH_SHORT).show();

        //reload activity
        setResult(RESULT_OK);

    }

    private void finishedEditing(){

        String newText = editor.getText().toString().trim();

        switch (action){

            case Intent.ACTION_INSERT:

                if(newText.length() ==0){
                    setResult(RESULT_CANCELED);
                }
                else{
                    insertNote(newText);
            }
                break;

            case Intent.ACTION_EDIT:

                if(newText.length() ==0){
                    //
                }
                else if(newText.equals(existingText)){
                    setResult(RESULT_CANCELED);
                }
                else{
                    updateNote(newText);
                }
        }
        finish();
    }

    @Override
    public void onBackPressed(){

        finishedEditing();
    }

}
