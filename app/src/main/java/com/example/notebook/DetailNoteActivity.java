package com.example.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.notebook.R.string.toolbar_title;

public class DetailNoteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NOTE_ID = "key_note_id";
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private Toolbar toolbar ;
    private TextView textNote;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);


        Intent getIntent = getIntent();



        // Title
        int noteId = getIntent.getIntExtra(KEY_NOTE_ID, -1);

        Note note = dao.getNote(noteId);
        //Toolbar
        toolbar =findViewById(R.id.toolbar);
        DetailNoteActivity.this.setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(note.getTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
        //Note text
        textNote = findViewById(R.id.text_note);
        textNote.setText(note.getText());
        // Button edit
        FloatingActionButton editNote = findViewById(R.id.buttonEdit);
        editNote.setOnClickListener(this);




        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra(KEY_NOTE_ID ,noteId );
        startActivity(intent);
    }

}