package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
    Note note = new Note();
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

         titleText = findViewById(R.id.title_text);
         noteText = findViewById(R.id.note_text);
         spinner = findViewById(R.id.spinner);

         selected = spinner.getSelectedItem().toString();
         Toast.makeText(getApplicationContext(), selected,Toast.LENGTH_SHORT).show();

         spinnerAdapter = new SpinnerAdapter(this);

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                note.setColor(spinnerAdapter.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(spinnerAdapter);
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
        selected = spinner.getSelectedItem().toString();

        if (item.getItemId() == R.id.actions_save) {
            if (id < 0) {
                note = new Note();
                note.setTitle(titleText.getText().toString());
                note.setText(noteText.getText().toString());
                note.setDate(getCurrentDate());
                note.setColor(selected);
                dao.insert(note);
                dao.update(note);
                closeKeyBoard();
                int x = 1 ; // edit type send flag
                System.out.println(x);
                intent.putExtra("id",x);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, "Заметка создана", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                note = dao.getNote(id);
                note.setTitle(titleText.getText().toString());
                note.setText(noteText.getText().toString());
                note.setDate(getCurrentDate());
                note.setColor(selected);
                dao.update(note);
                Toast.makeText(this, "Изменение сохранено ", Toast.LENGTH_SHORT).show();
                finish();
            }

; }

        return super.onOptionsItemSelected(item);
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


}
