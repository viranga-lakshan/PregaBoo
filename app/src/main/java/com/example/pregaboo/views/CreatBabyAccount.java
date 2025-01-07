package com.example.pregaboo.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Baby;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatBabyAccount extends AppCompatActivity {
    private EditText editTextName, editTextGender, editTextDOB, editTextDeliveryMethod;
    private Button buttonSave;
    private FirebaseFirestore db;
    private String momId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_baby_account);

        editTextName = findViewById(R.id.editTextName);
        editTextGender = findViewById(R.id.editTextGender);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextDeliveryMethod = findViewById(R.id.editTextDeliveryMethod);
        buttonSave = findViewById(R.id.buttonSave);
        db = FirebaseFirestore.getInstance();

        momId = getIntent().getStringExtra("MOM_ID"); // Retrieve the momId

        buttonSave.setOnClickListener(v -> saveBabyDetails());
    }

    private void saveBabyDetails() {
        String name = editTextName.getText().toString();
        String gender = editTextGender.getText().toString();
        String dob = editTextDOB.getText().toString();
        String deliveryMethod = editTextDeliveryMethod.getText().toString();

        if (name.isEmpty() || gender.isEmpty() || dob.isEmpty() || deliveryMethod.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user ID
        Baby baby = new Baby(null, name, gender, dob, deliveryMethod, userId);

        // Save baby details in the user's sub-collection
        db.collection("users")
            .document(userId) // Reference to the user document
            .collection("babies") // Sub-collection for babies
            .add(baby)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(CreatBabyAccount.this, "Baby details saved successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            })
            .addOnFailureListener(e -> {
                Toast.makeText(CreatBabyAccount.this, "Error saving baby details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }
}