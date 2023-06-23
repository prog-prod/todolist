package com.android.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioGroup radioGroupPriority;
    private RadioButton radioButtonLowPriority;
    private RadioButton radioButtonMediumPriority;
    private Button buttonSave;
    private NoteDatabase noteDatabase;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteDatabase = NoteDatabase.getInstance(getApplication());
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String text = editTextNote.getText().toString().trim();
        if(text.isEmpty()){
            Toast.makeText(this, getString(R.string.text_empty),Toast.LENGTH_SHORT).show();
        }
        int priority = getPriority();


        Note note = new Note(text, priority);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().add(note);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddNoteActivity.this, getString(R.string.note_is_added),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        thread.start();
    }

    private int getPriority() {
        int priority;

        if(radioButtonLowPriority.isChecked()){
            priority = 0;
        } else if (radioButtonMediumPriority.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        buttonSave = findViewById(R.id.buttonSave);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }
}