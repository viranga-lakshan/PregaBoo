package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.pregaboo.models.Vaccine;
import com.example.pregaboo.adapters.VaccineAdapter;

public class AddVaccienceMom extends AppCompatActivity {
    private EditText vaccineInput, dosageInput;
    private Button addButton;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccience_mom);

        db = FirebaseFirestore.getInstance();
        vaccineInput = findViewById(R.id.vaccine_input);
        dosageInput = findViewById(R.id.dosage_input);
        addButton = findViewById(R.id.add_button);
        RecyclerView vaccineRecyclerView = findViewById(R.id.vaccine_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null) {
            Toast.makeText(this, "Error: User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Vaccine> vaccineList = new ArrayList<>();
        VaccineAdapter vaccineAdapter = new VaccineAdapter(vaccineList, this);
        vaccineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vaccineRecyclerView.setAdapter(vaccineAdapter);

        addButton.setOnClickListener(v -> saveVaccineData());

        loadVaccineData(vaccineList, vaccineAdapter);
    }

    private void loadVaccineData(List<Vaccine> vaccineList, VaccineAdapter vaccineAdapter) {
        db.collection("users")
                .document(userId)
                .collection("UserVaccienceDetails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        vaccineList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Vaccine vaccine = document.toObject(Vaccine.class);
                            vaccineList.add(vaccine);
                        }
                        vaccineAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddVaccienceMom.this, "Error loading vaccine data.", Toast.LENGTH_SHORT).show();
                    }
                });
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
                .collection("UserVaccienceDetails")
                .add(vaccineData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddVaccienceMom.this, "Vaccine data saved successfully", Toast.LENGTH_SHORT).show();
                    vaccineInput.setText("");
                    dosageInput.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVaccienceMom.this, "Error saving vaccine data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}