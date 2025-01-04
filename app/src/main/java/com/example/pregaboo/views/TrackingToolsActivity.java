package com.example.pregaboo.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.widget.ImageButton;
import com.example.pregaboo.R;

public class TrackingToolsActivity extends AppCompatActivity {
    private CardView pregnancyTrackerCard;
    private ImageButton weightTrackerImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_tools);

        weightTrackerImageButton = findViewById(R.id.weightTrackerImageButton);
        pregnancyTrackerCard = findViewById(R.id.pregnancyTrackerCard);
        
        weightTrackerImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrackingToolsActivity.this, ShowMotherWeight.class);
            startActivity(intent);
        });

        pregnancyTrackerCard.setOnClickListener(v -> {
            Intent intent = new Intent(TrackingToolsActivity.this, ShowMotherWeight.class);
            startActivity(intent);
        });
    }
} 