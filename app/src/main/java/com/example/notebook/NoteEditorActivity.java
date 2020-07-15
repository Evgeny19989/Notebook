package com.example.notebook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.notebook.DetailNoteActivity.KEY_NOTE_ID;

public class NoteEditorActivity extends AppCompatActivity {


    public static String note_Title;
    public static String note_Text;
    public static String title; // intent putExtra
    public static String id;
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
        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_NOTE_ID , -1);

        if(id > 0){
            Toast.makeText(this, "EDIT ", Toast.LENGTH_SHORT).show();
        }
        else{
            if (item.getItemId() == R.id.actions_save) {

                Note note = new Note();
                note.setTitle(titleText.getText().toString());
                note.setText(noteText.getText().toString());
                dao.insert(note);
                closeKeyBoard();
                Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_SHORT).show();
            }
        }







        return super.onOptionsItemSelected(item);
    }

   private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken() ,0);
        }
   }
}