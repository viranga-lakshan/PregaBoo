package com.example.pregaboo.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.database.DataManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DataManager dataManager;
    private ImageButton profileButton;
    private ImageButton socialButton;
    private ImageButton shopButton;
    private ImageButton btnMomDetails;
    private ImageButton btn_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        dataManager = new DataManager(this);

        checkUserAuthentication();
        setupClickListeners();

        profileButton = findViewById(R.id.profileButton);
        socialButton = findViewById(R.id.socialButton);
        shopButton = findViewById(R.id.shopButton);
        btnMomDetails = findViewById(R.id.btn_mom_details);
        btn_food = findViewById(R.id.btn_food);

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MomProfileActivity.class);
            String momId = getCurrentMomId();
            if (momId != null) {
                intent.putExtra("MOM_ID", momId);
                startActivity(intent);
            } else {
                Log.e("DashboardActivity", "MOM_ID is null. Cannot start MomProfileActivity.");
                Toast.makeText(this, "Error: MOM_ID is missing.", Toast.LENGTH_SHORT).show();
            }
        });

        socialButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SocialActivity.class);
            startActivity(intent);
        });

        shopButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ShowProducteUsers.class);
            startActivity(intent);
        });

        btnMomDetails.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MomSeeEachDetailsDashboard.class);
            String momId = getCurrentMomId();
            if (momId != null) {
                intent.putExtra("MOM_ID", momId);
                startActivity(intent);
            } else {
                Log.e("DashboardActivity", "MOM_ID is null. Cannot start MomSeeEachDetailsDashboard.");
                Toast.makeText(this, "Error: MOM_ID is missing.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_food.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FoodVideo.class);
            startActivity(intent);
        });
    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String momId = currentUser.getUid();
            saveMomId(momId);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserAuthentication();
    }

    private void setupClickListeners() {
        ImageButton exerciseButton = findViewById(R.id.btn_exercises);
        ImageButton videoButton = findViewById(R.id.btn_video);
        ImageButton trackButton = findViewById(R.id.tarch_btn);

        exerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseActivity.class);
            startActivity(intent);
        });

        videoButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoPlayerActivity.class);
            startActivity(intent);
        });

        trackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrackingToolsActivity.class);
            startActivity(intent);
        });
    }

    private String getCurrentMomId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("MOM_ID", null);
    }

    private void saveMomId(String momId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MOM_ID", momId);
        editor.apply();
    }
}