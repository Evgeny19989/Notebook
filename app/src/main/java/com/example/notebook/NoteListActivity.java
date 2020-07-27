package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private   NotesAdapter adapter;
    private List<Note> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RecyclerView noteList = findViewById(R.id.noteList);
        noteList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        notes = dao.getNotes();
        adapter = new NotesAdapter(notes);
        adapter.setOnClickItemListener(
                noteId -> {
                    Intent intent = new Intent(this, DetailNoteActivity.class);
                    intent.putExtra(DetailNoteActivity.KEY_NOTE_ID, noteId);
                    startActivity(intent);

                }
        );
        noteList.setAdapter(adapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        System.out.println("create Menu");
        return true;
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
        boolean Flag = data.getBooleanExtra("EditActivity" , false);
           if(Flag){
               adapter.setNotes(dao.getNotes());
               adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
        case R.id.sort_date:
            if (item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);
            notes = dao.getNotes();
            Collections.sort(notes,ToDoListComparator.getDateComparator());
            adapter.setNotes(notes);
            adapter.notifyDataSetChanged();
            break;

        case R.id.sort_title:
            if (item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);
            notes = dao.getNotes();
            Collections.sort(notes,ToDoListComparator.getNameComparator());
            adapter.setNotes(notes);
            adapter.notifyDataSetChanged();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setNotes(dao.getNotes());
        adapter.notifyDataSetChanged();


    }


}

