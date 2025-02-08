package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.VaccineAdapter;
import com.example.pregaboo.models.Vaccine;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowVaccienceBaby extends AppCompatActivity {
    private RecyclerView vaccineRecyclerView;
    private VaccineAdapter vaccineAdapter;
    private List<Vaccine> vaccineList;
    private FirebaseFirestore db;
    private String userId;
    private String babyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vaccience_baby);

        db = FirebaseFirestore.getInstance();
        vaccineRecyclerView = findViewById(R.id.vaccine_recycler_view);

        vaccineList = new ArrayList<>();
        vaccineAdapter = new VaccineAdapter(vaccineList, this);
        vaccineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vaccineRecyclerView.setAdapter(vaccineAdapter);

        userId = getIntent().getStringExtra("USER_ID");
        babyId = getIntent().getStringExtra("BABY_ID");

        if (babyId == null || userId == null) {
            Toast.makeText(this, "Error: Baby ID or User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadVaccineData();
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
                        Toast.makeText(ShowVaccienceBaby.this, "Error loading vaccine data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}