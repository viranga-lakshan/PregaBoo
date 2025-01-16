package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.ClinicDateAdapter;
import com.example.pregaboo.models.ClinicDate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AddBabyClinicDateShedule extends AppCompatActivity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText purposeInput;
    private Button addButton;
    private RecyclerView clinicDateRecyclerView;
    private ClinicDateAdapter clinicDateAdapter;
    private List<ClinicDate> clinicDateList;
    private FirebaseFirestore db;
    private String userId;
    private String babyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby_clinic_date_shedule);

        db = FirebaseFirestore.getInstance();
        datePicker = findViewById(R.id.visit_date_picker);
        timePicker = findViewById(R.id.visit_time_picker);
        purposeInput = findViewById(R.id.purpose_input);
        addButton = findViewById(R.id.save_button);
        clinicDateRecyclerView = findViewById(R.id.dates_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");
        babyId = getIntent().getStringExtra("BABY_ID");

        if (babyId == null || userId == null) {
            Toast.makeText(this, "Error: Baby ID or User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        clinicDateList = new ArrayList<>();
        clinicDateAdapter = new ClinicDateAdapter(clinicDateList, this);
        clinicDateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        clinicDateRecyclerView.setAdapter(clinicDateAdapter);

        addButton.setOnClickListener(v -> saveClinicDate());
        loadClinicDates();
    }

    private void saveClinicDate() {
        String purpose = purposeInput.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        String date = day + "/" + (month + 1) + "/" + year;

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String time = String.format("%02d:%02d", hour, minute);

        if (purpose.isEmpty()) {
            Toast.makeText(this, "Please enter the purpose", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> clinicData = new HashMap<>();
        clinicData.put("date", date);
        clinicData.put("time", time);
        clinicData.put("purpose", purpose);

        db.collection("users")
                .document(userId)
                .collection("babies")
                .document(babyId)
                .collection("BabyClinicSchedule")
                .add(clinicData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddBabyClinicDateShedule.this, "Clinic date saved successfully", Toast.LENGTH_SHORT).show();
                    purposeInput.setText("");
                    loadClinicDates();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddBabyClinicDateShedule.this, "Error saving clinic date: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadClinicDates() {
        db.collection("users")
                .document(userId)
                .collection("babies")
                .document(babyId)
                .collection("BabyClinicSchedule")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        clinicDateList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ClinicDate clinicDate = document.toObject(ClinicDate.class);
                            clinicDate.setId(document.getId());
                            clinicDateList.add(clinicDate);
                        }
                        clinicDateAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddBabyClinicDateShedule.this, "Error loading clinic dates.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}