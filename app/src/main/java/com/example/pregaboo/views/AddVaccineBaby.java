package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.VaccineAdapter;
import com.example.pregaboo.models.Vaccine;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddVaccineBaby extends AppCompatActivity {
    private EditText vaccineInput, dosageInput;
    private Button addButton;
    private RecyclerView vaccineRecyclerView;
    private VaccineAdapter vaccineAdapter;
    private List<Vaccine> vaccineList;
    private FirebaseFirestore db;
    private String userId;
    private String babyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccine_baby);

        db = FirebaseFirestore.getInstance();
        vaccineInput = findViewById(R.id.vaccine_input);
        dosageInput = findViewById(R.id.dosage_input);
        addButton = findViewById(R.id.add_button);
        vaccineRecyclerView = findViewById(R.id.vaccine_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");
        babyId = getIntent().getStringExtra("BABY_ID");

        if (babyId == null || userId == null) {
            Toast.makeText(this, "Error: Baby ID or User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        vaccineList = new ArrayList<>();
        vaccineAdapter = new VaccineAdapter(vaccineList, this);
        vaccineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vaccineRecyclerView.setAdapter(vaccineAdapter);

        addButton.setOnClickListener(v -> saveVaccineData());
        loadVaccineData();
    }

    private void saveVaccineData() {
        String vaccineName = vaccineInput.getText().toString().trim();
        String dosage = dosageInput.getText().toString().trim();

        if (vaccineName.isEmpty() || dosage.isEmpty()) {
            Toast.makeText(this, "Please enter both vaccine name and dosage", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(new Date());

        Map<String, Object> vaccineData = new HashMap<>();
        vaccineData.put("date", formattedDate);
        vaccineData.put("vaccineName", vaccineName);
        vaccineData.put("dosage", dosage);

        db.collection("users")
                .document(userId)
                .collection("babies")
                .document(babyId)
                .collection("BabyVaccines")
                .add(vaccineData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddVaccineBaby.this, "Vaccine data saved successfully", Toast.LENGTH_SHORT).show();
                    vaccineInput.setText("");
                    dosageInput.setText("");
                    loadVaccineData(); // Refresh the RecyclerView
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVaccineBaby.this, "Error saving vaccine data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadVaccineData() {
        db.collection("users")
                .document(userId)
                .collection("babies")
                .document(babyId)
                .collection("BabyVaccines")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        vaccineList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Vaccine vaccine = document.toObject(Vaccine.class);
                            vaccine.setId(document.getId()); // Set the document ID
                            vaccineList.add(vaccine);
                        }
                        vaccineAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddVaccineBaby.this, "Error loading vaccine data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}