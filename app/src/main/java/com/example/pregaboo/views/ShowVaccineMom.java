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

public class ShowVaccineMom extends AppCompatActivity {
    private FirebaseFirestore db;
    private String momId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vaccine_mom);

        db = FirebaseFirestore.getInstance();
        RecyclerView vaccineRecyclerView = findViewById(R.id.vaccine_recycler_view);

        momId = getIntent().getStringExtra("MOM_ID");

        if (momId == null) {
            Toast.makeText(this, "Error: Mom ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Vaccine> vaccineList = new ArrayList<>();
        VaccineAdapter vaccineAdapter = new VaccineAdapter(vaccineList, this);
        vaccineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vaccineRecyclerView.setAdapter(vaccineAdapter);

        loadVaccineData(vaccineList, vaccineAdapter);
    }

    private void loadVaccineData(List<Vaccine> vaccineList, VaccineAdapter vaccineAdapter) {
        db.collection("users")
                .document(momId)
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
                        Toast.makeText(ShowVaccineMom.this, "Error loading vaccine data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}