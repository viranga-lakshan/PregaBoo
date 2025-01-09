package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.BabyAdapter;
import com.example.pregaboo.models.Baby;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import com.example.pregaboo.views.CreatePostActivity;

public class MomProfileActivity extends AppCompatActivity {
    private RecyclerView babyRecyclerView;
    private BabyAdapter babyAdapter;
    private List<Baby> babyList;
    private FirebaseFirestore db;
    private String momId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_profile);

        db = FirebaseFirestore.getInstance();
        momId = getIntent().getStringExtra("MOM_ID");

        if (momId == null) {
            Log.e("MomProfileActivity", "MOM_ID is null. Cannot fetch babies.");
            Toast.makeText(this, "Error: MOM_ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        babyRecyclerView = findViewById(R.id.babyRecyclerView);
        babyList = new ArrayList<>();
        babyAdapter = new BabyAdapter(babyList, this);
        babyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        babyRecyclerView.setAdapter(babyAdapter);

        fetchBabies();

        Button addPostButton = findViewById(R.id.btn_addpost);
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomProfileActivity.this, CreatePostActivity.class);
            startActivity(intent);
        });
    }

    private void fetchBabies() {
        db.collection("users")
            .document(momId)
            .collection("babies")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    babyList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Baby baby = document.toObject(Baby.class);
                        baby.setBabyId(document.getId());
                        babyList.add(baby);
                    }
                    babyAdapter.notifyDataSetChanged();
                } else {
                    Log.e("MomProfileActivity", "Error fetching babies: " + task.getException().getMessage());
                }
            });
    }
} 