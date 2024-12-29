package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pregaboo.R;

public class OnboardingThreeActivity extends AppCompatActivity {
    private Button buttonSkip;
    private Button buttonNext;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_three);

        buttonSkip = findViewById(R.id.buttonSkip);
        buttonNext = findViewById(R.id.buttonNext);

        dots = new ImageView[3];
        dots[0] = findViewById(R.id.dot1);
        dots[1] = findViewById(R.id.dot2);
        dots[2] = findViewById(R.id.dot3);

        buttonSkip.setOnClickListener(v -> finishOnboarding());
        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(this, OnboardingFourActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void finishOnboarding() {
        // TODO: Navigate to main app
        finish();
    }
} 