package com.example.notebook;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailNoteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NOTE_ID = "key_note_id";
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private int noteId;
    private TextView textNote;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        Intent getIntent = getIntent();

        noteId = getIntent.getIntExtra(KEY_NOTE_ID, -1);
        Note note = dao.getNote(noteId);

        toolbar = findViewById(R.id.toolbar);
        DetailNoteActivity.this.setSupportActionBar(toolbar);
        toolbar.setTitle(note.getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        textNote = findViewById(R.id.text_note);
        textNote.setText(note.getText());

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

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle(dao.getNote(noteId).getTitle());
        textNote.setText(dao.getNote(noteId).getText());
        textNote.setBackgroundColor(Color.parseColor(dao.getNote(noteId).getColor()));

    }
}