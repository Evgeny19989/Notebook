package com.example.notebook;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private   NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RecyclerView noteList = findViewById(R.id.noteList);
        noteList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        List<Note> notes = dao.getNotes();

        adapter = new NotesAdapter(notes);
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

                dao.delete(adapter.getNoteAt(target.getAdapterPosition()));
                adapter.setNotes(dao.getNotes());
                adapter.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(noteList);




    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){return;}
        if(requestCode == 1) {
            int id = data.getIntExtra("id",-1);
            System.out.println(id);

            adapter.setNotes(dao.getNotes());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setNotes(dao.getNotes());
        adapter.notifyDataSetChanged();


    }
}

