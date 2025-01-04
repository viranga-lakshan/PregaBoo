package com.example.pregaboo.controllers;

import com.example.pregaboo.models.Weight;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

public class WeightController {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public WeightController() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<Void> saveWeight(int week, double weight) {
        String userId = mAuth.getCurrentUser().getUid();
        Weight weightData = new Weight(week, weight);

        return db.collection("users")
                .document(userId)
                .collection("weight")
                .document("week_" + week)
                .set(weightData);
    }

    public Task<QuerySnapshot> getWeights() {
        String userId = mAuth.getCurrentUser().getUid();
        return db.collection("users")
                .document(userId)
                .collection("weight")
                .orderBy("week")
                .get();
    }
} 