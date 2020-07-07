package com.example.notebook;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


public class NoteEditorActivity extends AppCompatActivity {


    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private EditText titleText;
    private EditText noteText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


         titleText = findViewById(R.id.title_text);
         noteText = findViewById(R.id.note_text);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.actions_save){
            Note note = new Note();
            note.setTitle(titleText.getText().toString());
            note.setText(noteText.getText().toString());

            dao.insert(note);
        }
        return super.onOptionsItemSelected(item);
    }
}