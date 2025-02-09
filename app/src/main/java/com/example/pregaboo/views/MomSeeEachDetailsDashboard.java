package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class MomSeeEachDetailsDashboard extends AppCompatActivity {
    private String momId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_see_each_details_dashboard);

        momId = getIntent().getStringExtra("MOM_ID");

        Button vaccineButton = findViewById(R.id.vaccineButton);
        vaccineButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomSeeEachDetailsDashboard.this, ShowVaccineMom.class);
            intent.putExtra("MOM_ID", momId);
            startActivity(intent);
        });

        Button scheduleButton = findViewById(R.id.scheduleButton);
        scheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomSeeEachDetailsDashboard.this, ShowSheduleMom.class);
            intent.putExtra("USER_ID", momId); // Pass the user ID here
            startActivity(intent);
        });

        Button growthButton = findViewById(R.id.growthButton);
        growthButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomSeeEachDetailsDashboard.this, ShowMomGrowth.class);
            intent.putExtra("MOM_ID", momId);
            startActivity(intent);
        });

        Button notesButton = findViewById(R.id.notesButton);
        notesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MomSeeEachDetailsDashboard.this, ShowMidwifeNotesMom.class);
            intent.putExtra("MOM_ID", momId);
            startActivity(intent);
        });
    }
}