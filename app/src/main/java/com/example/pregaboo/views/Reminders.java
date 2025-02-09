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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.pregaboo.models.Reminder;
import com.example.pregaboo.adapters.ReminderAdapter;

public class Reminders extends AppCompatActivity {
    private EditText noteInput;
    private TextView dateInput;
    private Button addButton;
    private FirebaseFirestore db;
    private String userId;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        db = FirebaseFirestore.getInstance();
        noteInput = findViewById(R.id.note_input);
        dateInput = findViewById(R.id.date_input);
        addButton = findViewById(R.id.add_button);
        RecyclerView reminderRecyclerView = findViewById(R.id.reminder_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null) {
            Toast.makeText(this, "Error: User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Reminder> reminderList = new ArrayList<>();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminderList, this);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reminderRecyclerView.setAdapter(reminderAdapter);

        calendar = Calendar.getInstance();

        dateInput.setOnClickListener(v -> showDatePickerDialog());

        addButton.setOnClickListener(v -> saveReminderData());

        loadReminderData(reminderList, reminderAdapter);
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

    private void loadReminderData(List<Reminder> reminderList, ReminderAdapter reminderAdapter) {
        db.collection("users")
                .document(userId)
                .collection("UserReminders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        reminderList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Reminder reminder = document.toObject(Reminder.class);
                            reminderList.add(reminder);
                        }
                        reminderAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Reminders.this, "Error loading reminder data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveReminderData() {
        String note = noteInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();

        if (note.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please enter both note and date", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> reminderData = new HashMap<>();
        reminderData.put("note", note);
        reminderData.put("date", date);

        db.collection("users")
                .document(userId)
                .collection("UserReminders")
                .add(reminderData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Reminders.this, "Reminder saved successfully", Toast.LENGTH_SHORT).show();
                    noteInput.setText("");
                    dateInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Reminders.this, "Error saving reminder: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}