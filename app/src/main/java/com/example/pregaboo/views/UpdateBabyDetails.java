package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Baby;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateBabyDetails extends AppCompatActivity {
    private String babyId;
    private String babyName;
    private FirebaseFirestore db;
    private TextView babyNameTextView;
    private Button growthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_baby_details);

        babyNameTextView = findViewById(R.id.txt_bay_name);
        growthButton = findViewById(R.id.growthButton);
        db = FirebaseFirestore.getInstance();
        babyId = getIntent().getStringExtra("BABY_ID");
        babyName = getIntent().getStringExtra("BABY_NAME");

        if (babyName != null) {
            babyNameTextView.setText(babyName);
        } else {
            fetchBabyDetails();
        }

        growthButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateBabyDetails.this, Bmichart.class);
            startActivity(intent);
        });
    }

    private void fetchBabyDetails() {
        db.collection("babies")
                .document(babyId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Baby baby = task.getResult().toObject(Baby.class);
                        if (baby != null) {
                            babyNameTextView.setText(baby.getName());
                        } else {
                            Log.e("UpdateBabyDetails", "Baby object is null");
                        }
                    } else {
                        Log.e("UpdateBabyDetails", "Error fetching baby details: " + task.getException().getMessage());
                    }
                });
    }
}