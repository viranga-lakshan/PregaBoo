package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class TrackingToolsActivity extends AppCompatActivity {
    private ImageButton weightTrackerImageButton;
    private ImageButton kickCounterImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_tools);

        weightTrackerImageButton = findViewById(R.id.weightTrackerImageButton);
        weightTrackerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingToolsActivity.this, ShowMotherWeight.class);
                startActivity(intent);
            }
        });

        kickCounterImageButton = findViewById(R.id.kickCounterImageButton);
        kickCounterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingToolsActivity.this, KickCounterActivity.class);
                startActivity(intent);
            }
        });
    }
}