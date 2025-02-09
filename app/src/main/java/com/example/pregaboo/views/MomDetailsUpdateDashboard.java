package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pregaboo.R;

public class MomDetailsUpdateDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mom_details_update_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String userId = getIntent().getStringExtra("USER_ID");

        Button vaccineButton = findViewById(R.id.vaccineButton);
        vaccineButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomDetailsUpdateDashboard.this, AddVaccienceMom.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        Button growthButton = findViewById(R.id.growthButton);
        growthButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomDetailsUpdateDashboard.this, MomBmiChart.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        Button notesButton = findViewById(R.id.notesButton);
        notesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomDetailsUpdateDashboard.this, MidwifeAddMomNotes.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
    }
}