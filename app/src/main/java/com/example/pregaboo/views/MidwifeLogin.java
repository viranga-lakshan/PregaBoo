package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MidwifeLogin extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        db = FirebaseFirestore.getInstance();

        // Assuming there's a button to trigger login
        findViewById(R.id.loginButton).setOnClickListener(v -> loginMidwife());
    }

    private void loginMidwife() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("midwives")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    if (result != null && !result.isEmpty()) {
                        // Login successful
                        String midwifeId = result.getDocuments().get(0).getId();
                        String location = result.getDocuments().get(0).getString("location");

                        Intent intent = new Intent(MidwifeLogin.this, ShowMoms.class);
                        intent.putExtra("MIDWIFE_ID", midwifeId);
                        intent.putExtra("Location", location);
                        startActivity(intent);
                        finish();
                    } else {
                        // No matching midwife found
                        Toast.makeText(MidwifeLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while querying Firestore
                    Toast.makeText(MidwifeLogin.this, "Authentication failed: " + task.getException().getMessage(),
                                 Toast.LENGTH_SHORT).show();
                }
            });
    }
}