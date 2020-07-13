package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailNoteActivity extends AppCompatActivity {

    public static final String KEY_NOTE_ID = "key_note_id";
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        Intent intent = getIntent();
        int noteId = intent.getIntExtra(KEY_NOTE_ID, -1);
        Note note = dao.getNote(noteId);

        Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();
    }
}