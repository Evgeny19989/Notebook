package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.notebook.DetailNoteActivity.KEY_NOTE_ID;

public class NoteEditorActivity extends AppCompatActivity {
    private NotebookDao dao = App.getInstance().getDatabase().getNotebookDao();
    private EditText titleText;
    private EditText noteText;
    private Spinner spinner ;
    SpinnerAdapter spinnerAdapter;
    Note note ;
    String selected;
    int id;
    Intent intent;
    int sizeText = 16 ;
    ImageView deleteTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

         titleText = findViewById(R.id.title_text);
         noteText = findViewById(R.id.note_text);
         deleteTitle = findViewById(R.id.delete_text_title);

         //spinner
         spinner = findViewById(R.id.spinner);
         selected = spinner.getSelectedItem().toString();
         spinnerAdapter = new SpinnerAdapter(this);
         spinner.setAdapter(spinnerAdapter);

        intent = getIntent();
        id = intent.getIntExtra(KEY_NOTE_ID , -1);

        if(id > 0) {
            note = dao.getNote(id);
            spinner.setSelection(spinnerAdapter.getColorIndex(note.getColor()));
            titleText.setText(note.getTitle());
            noteText.setText(note.getText());
        }

        deleteTitle.setOnClickListener(v -> titleText.setText(""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        selected = spinner.getSelectedItem().toString();

        switch(item.getItemId()) {
           case R.id.actions_save:
            if (id < 0) {
                note = new Note();
                note.setTitle(titleText.getText().toString());
                note.setText(noteText.getText().toString());
                note.setDate(getCurrentDate());
                note.setColor(selected);
                dao.insert(note);
                dao.update(note);
                closeKeyBoard();
                intent.putExtra("EditActivity" , true);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "Заметка создана", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                note.setTitle(titleText.getText().toString());
                note.setText(noteText.getText().toString());
                note.setColor(selected);
                dao.update(note);
                Toast.makeText(this, "Изменение сохранено ", Toast.LENGTH_SHORT).show();
                closeKeyBoard();
                finish();
            }
            break;
           case R.id.size_text:
               noteText.setTextSize(sizeText += 4);
               if(sizeText == 28) {sizeText = 16;}
               break;

}       return super.onOptionsItemSelected(item);
    }

   private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken() ,0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getCurrentDate(){
        Calendar now = Calendar.getInstance();
        SimpleDateFormat myDate = new SimpleDateFormat("dd.MM.yyyy" ,Locale.ENGLISH);
        return myDate.format(now.getTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeKeyBoard();
    }
}
