package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MomProfileActivity extends AppCompatActivity {
    private TextView userName, userDetails;
    private ImageView btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_profile);

        mAuth = FirebaseAuth.getInstance();
        initializeViews();
        setupUserData();
        setupClickListeners();
    }

    private void initializeViews() {
        userName = findViewById(R.id.userName);
        userDetails = findViewById(R.id.userDetails);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userName.setText(user.getDisplayName());
            userDetails.setText(user.getEmail());
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
} 