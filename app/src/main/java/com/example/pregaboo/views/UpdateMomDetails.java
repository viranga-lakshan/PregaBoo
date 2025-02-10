package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.BabyAdapter;
import com.example.pregaboo.models.Baby;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class UpdateMomDetails extends AppCompatActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String momId;
    private RecyclerView babyRecyclerView;
    private BabyAdapter babyAdapter;
    private List<Baby> babyList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_mom_details);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("UpdateMomDetails", "View with ID 'main' not found.");
        }

        db = FirebaseFirestore.getInstance();
        momId = getIntent().getStringExtra("MOM_ID");

        babyRecyclerView = findViewById(R.id.babyRecyclerView);
        babyList = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User is not logged in, redirect to login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String uid = currentUser.getUid(); // Now safe to call getUid()

        babyAdapter = new BabyAdapter(babyList, this, uid);
        babyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        babyRecyclerView.setAdapter(babyAdapter);

        fetchBabies();

        Button addChildButton = findViewById(R.id.addChildButton);
        addChildButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateMomDetails.this, CreatBabyAccount.class);
            intent.putExtra("MOM_ID", momId);
            startActivity(intent);
        });

        Button updateMomDetailsButton = findViewById(R.id.updateMomDetailsButton);
        updateMomDetailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateMomDetails.this, MomDetailsUpdateDashboard.class);
            intent.putExtra("USER_ID", uid);
            startActivity(intent);
        });

        // Check Google Play Services before Firebase operations
        if (!checkPlayServices()) {
            Toast.makeText(this, "This device is not supported", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    private void fetchBabies() {
        db.collection("users")
                .document(momId)
                .collection("babies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        babyList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Baby baby = document.toObject(Baby.class);
                            baby.setBabyId(document.getId());
                            babyList.add(baby);
                        }
                        babyAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("UpdateMomDetails", "Error fetching babies: " + task.getException().getMessage());
                    }
                });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("UpdateMomDetails", "This device is not supported.");
                return false;
            }
            return false;
        }
        return true;
    }
}