package com.example.pregaboo.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ImageView;
import com.example.pregaboo.R;

public class TrackingToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_tools);

        // Set up back button click listener
        LinearLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
} 