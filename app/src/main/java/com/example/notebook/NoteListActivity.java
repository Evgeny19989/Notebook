package com.example.notebook;

import android.content.Intent;
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
    private Toolbar toolbar;
    private TextView preview_text;
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RecyclerView noteList = findViewById(R.id.noteList);
        noteList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(this);

        preview_text = findViewById(R.id.preview_text);

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




                adapter.notifyDataSetChanged();
            }
        });
        if (!notes.isEmpty()) {
            preview_text.setText("");
        }



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
            int id = data.getIntExtra("id", -1);
            System.out.println(id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}

