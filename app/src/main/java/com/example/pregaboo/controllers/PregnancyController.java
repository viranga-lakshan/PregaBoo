package com.example.pregaboo.controllers;

import com.example.pregaboo.models.PregnancyData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;

public class PregnancyController {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public PregnancyController() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<Void> savePregnancyData(PregnancyData pregnancyData) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Map<String, Object> data = new HashMap<>();
        
        // Add user data from Google Sign In
        data.put("id", currentUser.getUid());
        data.put("name", currentUser.getDisplayName());
        data.put("email", currentUser.getEmail());
        
        // Add pregnancy data
        data.put("contact", pregnancyData.getContactNumber());
        data.put("location", pregnancyData.getDistrict());
        data.put("pregnancyDate", pregnancyData.getLastPeriodDate());

        return db.collection("users")
                .document(pregnancyData.getUserId())
                .set(data);
    }
} 