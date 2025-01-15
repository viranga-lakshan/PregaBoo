package com.example.pregaboo.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Bmichart extends AppCompatActivity {
    private EditText heightInput, weightInput, milestonesInput;
    private Button saveButton;
    private FirebaseFirestore db;
    private String userId;
    private String babyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmichart);

        db = FirebaseFirestore.getInstance();
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        milestonesInput = findViewById(R.id.milestonesInput);
        saveButton = findViewById(R.id.saveButton);

        userId = getIntent().getStringExtra("USER_ID");
        babyId = getIntent().getStringExtra("BABY_ID");

        if (babyId == null || userId == null) {
            Toast.makeText(this, "Error: Baby ID or User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        saveButton.setOnClickListener(v -> saveGrowthData());
    }

    private void saveGrowthData() {
        String heightStr = heightInput.getText().toString().trim();
        String weightStr = weightInput.getText().toString().trim();
        String milestones = milestonesInput.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        Map<String, Object> growthData = new HashMap<>();
        growthData.put("date", System.currentTimeMillis());
        growthData.put("height", height);
        growthData.put("weight", weight);
        growthData.put("milestones", milestones);

        db.collection("users")
            .document(userId)
            .collection("babies")
            .document(babyId)
            .collection("growth")
            .add(growthData)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(Bmichart.this, "Growth data saved successfully", Toast.LENGTH_SHORT).show();
                heightInput.setText("");
                weightInput.setText("");
                milestonesInput.setText("");
            })
            .addOnFailureListener(e -> {
                Toast.makeText(Bmichart.this, "Error saving growth data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }
} 