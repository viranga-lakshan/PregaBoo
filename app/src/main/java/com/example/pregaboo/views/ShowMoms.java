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

    // Declare Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_moms);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        momRecyclerView = findViewById(R.id.momRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        noMomsTextView = findViewById(R.id.noMomsTextView);

        // Initialize the momList and momAdapter
        momList = new ArrayList<>();
        momAdapter = new MomAdapter(momList, this);

        // Set up the RecyclerView
        momRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        momRecyclerView.setAdapter(momAdapter);

        // Fetch moms data
        fetchMoms("midwifeId"); // Replace with actual midwife ID
    }

    private void fetchMoms(String midwifeId) {
        progressBar.setVisibility(View.VISIBLE);
        noMomsTextView.setVisibility(View.GONE);

        String midwifeDistrict = "Kalutara"; // Replace with actual district fetching logic

        // Use the Firestore instance to query the database
        db.collection("users")
            .whereEqualTo("location", midwifeDistrict)
            .get()
            .addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    momList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("FirestoreData", document.getId() + " => " + document.getData());
                        Mom mom = document.toObject(Mom.class);
                        momList.add(mom);
                    }
                    momAdapter.notifyDataSetChanged(); // This should now work

                    if (momList.isEmpty()) {
                        noMomsTextView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e("FirestoreError", "Error getting moms: ", task.getException());
                    Toast.makeText(ShowMoms.this, "Error getting moms: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}