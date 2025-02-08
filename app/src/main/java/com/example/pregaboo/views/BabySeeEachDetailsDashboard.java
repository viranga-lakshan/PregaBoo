package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.pregaboo.R;

public class BabySeeEachDetailsDashboard extends AppCompatActivity {
    private String babyId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_see_each_details_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        babyId = getIntent().getStringExtra("BABY_ID");
        userId = getIntent().getStringExtra("USER_ID");


        Button vaccineButton = findViewById(R.id.vaccineButton);
        vaccineButton.setOnClickListener(v -> {
            Intent intent = new Intent(BabySeeEachDetailsDashboard.this, ShowVaccienceBaby.class);
            intent.putExtra("BABY_ID", babyId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        Button scheduleButton = findViewById(R.id.scheduleButton);
        scheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(BabySeeEachDetailsDashboard.this, ShowBabyShedule.class);
            intent.putExtra("BABY_ID", babyId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
    }
}