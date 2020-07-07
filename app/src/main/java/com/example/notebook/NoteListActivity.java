package com.example.notebook;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView preview_text;
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView noteList = findViewById(R.id.noteList);
        noteList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        List<Note> notes = dao.getNotes();
        NotesAdapter adapter = new NotesAdapter(notes);
        noteList.setAdapter(adapter);
        //Toast.makeText(this, notes.size()+"", Toast.LENGTH_LONG).show();

        preview_text = findViewById(R.id.preview_text);
        if(!notes.isEmpty()){
            preview_text.setText("");
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this , NoteEditorActivity.class);
        startActivity(intent);
    }
}


