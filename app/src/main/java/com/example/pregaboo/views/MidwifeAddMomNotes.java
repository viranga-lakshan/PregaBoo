package com.example.pregaboo.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.pregaboo.models.Note;
import com.example.pregaboo.adapters.NoteAdapter;

public class MidwifeAddMomNotes extends AppCompatActivity {
    private EditText noteInput;
    private TextView dateInput;
    private Button addButton;
    private FirebaseFirestore db;
    private String userId;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife_add_mom_notes);

        db = FirebaseFirestore.getInstance();
        noteInput = findViewById(R.id.note_input);
        dateInput = findViewById(R.id.date_input);
        addButton = findViewById(R.id.add_button);
        RecyclerView notesRecyclerView = findViewById(R.id.notes_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null) {
            Toast.makeText(this, "Error: User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Note> noteList = new ArrayList<>();
        NoteAdapter noteAdapter = new NoteAdapter(noteList, this);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(noteAdapter);

        calendar = Calendar.getInstance();

        dateInput.setOnClickListener(v -> showDatePickerDialog());

        addButton.setOnClickListener(v -> saveNoteData());

        loadNoteData(noteList, noteAdapter);
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            calendar.set(year1, month1, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateInput.setText(dateFormat.format(calendar.getTime()));
        }, year, month, day);

        datePickerDialog.show();
    }

    private void loadNoteData(List<Note> noteList, NoteAdapter noteAdapter) {
        db.collection("users")
                .document(userId)
                .collection("MidwifeNotes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        noteList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Note note = document.toObject(Note.class);
                            noteList.add(note);
                        }
                        noteAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MidwifeAddMomNotes.this, "Error loading notes data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveNoteData() {
        String note = noteInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();

        if (note.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please enter both note and date", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> noteData = new HashMap<>();
        noteData.put("note", note);
        noteData.put("date", date);

        db.collection("users")
                .document(userId)
                .collection("MidwifeNotes")
                .add(noteData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MidwifeAddMomNotes.this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                    noteInput.setText("");
                    dateInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MidwifeAddMomNotes.this, "Error saving note: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}