package com.example.pregaboo.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pregaboo.R;
import com.example.pregaboo.adapters.MomAdapter;
import com.example.pregaboo.models.Mom;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowMoms extends AppCompatActivity {
    private RecyclerView momRecyclerView;
    private MomAdapter momAdapter;
    private List<Mom> momList;
    private ProgressBar progressBar;
    private TextView noMomsTextView;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_moms);

        db = FirebaseFirestore.getInstance();

        momRecyclerView = findViewById(R.id.momRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        noMomsTextView = findViewById(R.id.noMomsTextView);

        momList = new ArrayList<>();
        momAdapter = new MomAdapter(momList, this);

        momRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        momRecyclerView.setAdapter(momAdapter);

        String midwifeId = getIntent().getStringExtra("MIDWIFE_ID");
        if (midwifeId != null) {
            fetchMidwifeLocation(midwifeId);
        } else {
            Toast.makeText(this, "Midwife ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchMidwifeLocation(String midwifeId) {
        progressBar.setVisibility(View.VISIBLE);
        noMomsTextView.setVisibility(View.GONE);

        db.collection("midwives")
                .document(midwifeId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        String midwifeDistrict = document.getString("location");
                        if (midwifeDistrict != null) {
                            fetchMoms(midwifeDistrict);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            noMomsTextView.setVisibility(View.VISIBLE);
                            Toast.makeText(ShowMoms.this, "Midwife district not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        noMomsTextView.setVisibility(View.VISIBLE);
                        Toast.makeText(ShowMoms.this, "Error fetching midwife data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchMoms(String midwifeDistrict) {
        progressBar.setVisibility(View.VISIBLE);
        noMomsTextView.setVisibility(View.GONE);

        db.collection("users")
                .whereEqualTo("location", midwifeDistrict)
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        momList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Mom mom = document.toObject(Mom.class);
                            mom.setMomId(document.getId());
                            momList.add(mom);
                        }
                        momAdapter.notifyDataSetChanged();

                        if (momList.isEmpty()) {
                            noMomsTextView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(ShowMoms.this, "Error getting moms: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}