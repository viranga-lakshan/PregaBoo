package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.MomAdapter;
import com.example.pregaboo.models.Mom;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowMoms extends AppCompatActivity {

    private RecyclerView momRecyclerView;
    private MomAdapter momAdapter;
    private List<Mom> momList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_moms);

        momRecyclerView = findViewById(R.id.momRecyclerView);
        momRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        momList = new ArrayList<>();
        momAdapter = new MomAdapter(momList);
        momRecyclerView.setAdapter(momAdapter);

        db = FirebaseFirestore.getInstance();
        String midwifeId = getIntent().getStringExtra("MIDWIFE_ID");

        if (midwifeId != null) {
            fetchMoms(midwifeId);
        }
    }

    private void fetchMoms(String midwifeId) {
        // Assuming you have a way to get the midwife's district
        String midwifeDistrict = "Kalutara"; // Replace with actual district fetching logic

        db.collection("users")
                .whereEqualTo("district", midwifeDistrict)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Mom mom = document.toObject(Mom.class);
                            momList.add(mom);
                        }
                        momAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShowMoms.this, "Error getting moms: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}