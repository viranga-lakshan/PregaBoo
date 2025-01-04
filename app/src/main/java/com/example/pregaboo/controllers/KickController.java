 package com.example.pregaboo.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class KickController extends AppCompatActivity {
    private ImageButton kickCounterImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_tools);

        kickCounterImageButton = findViewById(R.id.kickCounterImageButton);
        kickCounterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KickController.this, KickCounterActivity.class);
                startActivity(intent);
            }
        });
    }
}