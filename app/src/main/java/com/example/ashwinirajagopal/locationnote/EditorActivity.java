package com.example.ashwinirajagopal.locationnote;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.net.Uri;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {


    //To determine the action - inserting or updating a note
    private String action;

    private EditText editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = (EditText) findViewById(R.id.editNote);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(NotesContentProvider.CONTENT);

        //if the add new note button is pressed URI is null
        if (uri == null) {

            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));

        }

    }

    public void insertNote(String text){

        ContentValues values = new ContentValues();
        values.put(DBHelper.NOTE_TEXT,text);

        //ContentResolver - android uses to resolve Uris to ContentProvider, here it is NotesContentProvider

        getContentResolver().insert(NotesContentProvider.CONTENT_URI, values);

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
        }
        finish();
    }

    @Override
    public void onBackPressed(){

        finishedEditing();
    }

}
