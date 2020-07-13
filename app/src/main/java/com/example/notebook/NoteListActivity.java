package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView preview_text;
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private TextView textTitle;


    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView noteList = findViewById(R.id.noteList);
        noteList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        preview_text = findViewById(R.id.preview_text);
        textTitle = findViewById(R.id.titleNote);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        List<Note> notes = dao.getNotes();

        NotesAdapter adapter = new NotesAdapter(notes);
        adapter.setOnClickItemListener(
                noteId -> {
                    Intent intent = new Intent(this, DetailNoteActivity.class);
                    intent.putExtra(DetailNoteActivity.KEY_NOTE_ID, noteId);
                    startActivity(intent);
                }
        );

        noteList.setAdapter(adapter);
        Toast.makeText(this, notes.size() + "", Toast.LENGTH_LONG).show();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {

                int pos = target.getAdapterPosition();
                dao.delete(notes.remove(pos));


                if (!notes.isEmpty()) {
                    preview_text.setText("");
                }


                if (notes.isEmpty()) {
                    textTitle.setText("");
                }

                adapter.notifyDataSetChanged();
            }
        });
        if (!notes.isEmpty()) {
            preview_text.setText("");
        }


        if (notes.isEmpty()) {
            textTitle.setText("");
        }
        helper.attachToRecyclerView(noteList);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
    }
}