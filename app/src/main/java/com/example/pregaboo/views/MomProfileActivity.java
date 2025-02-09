package com.example.pregaboo.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.BabyAdapterForDetailsDashboard;
import com.example.pregaboo.models.Baby;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class MomProfileActivity extends AppCompatActivity {
    private RecyclerView babyRecyclerView;
    private BabyAdapterForDetailsDashboard babyAdapter;
    private List<Baby> babyList;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_profile);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        babyRecyclerView = findViewById(R.id.babyRecyclerView);
        babyList = new ArrayList<>();

        babyAdapter = new BabyAdapterForDetailsDashboard(babyList, this, userId);
        babyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        babyRecyclerView.setAdapter(babyAdapter);

        fetchBabies();
    }

    private void fetchBabies() {
        db.collection("users")
                .document(userId)
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
                    }
                });
    }
}