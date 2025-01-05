package com.example.pregaboo.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Midwife;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminCreateMidwifeAccount extends AppCompatActivity {
    private EditText midwifeNameInput;
    private EditText midwifeEmailInput;
    private EditText midwifePhoneInput;
    private EditText midwifePasswordInput;
    private Spinner midwifeDistrictSpinner;
    private Button createMidwifeButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_midwife_account);

        midwifeNameInput = findViewById(R.id.midwifeNameInput);
        midwifeEmailInput = findViewById(R.id.midwifeEmailInput);
        midwifePhoneInput = findViewById(R.id.midwifePhoneInput);
        midwifePasswordInput = findViewById(R.id.midwifePasswordInput);
        midwifeDistrictSpinner = findViewById(R.id.midwifeDistrictSpinner);
        createMidwifeButton = findViewById(R.id.createMidwifeButton);
        db = FirebaseFirestore.getInstance();

        // Populate the Spinner with district options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sri_lanka_districts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        midwifeDistrictSpinner.setAdapter(adapter);

        createMidwifeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMidwifeAccount();
            }
        });
    }

    private void createMidwifeAccount() {
        String name = midwifeNameInput.getText().toString().trim();
        String email = midwifeEmailInput.getText().toString().trim();
        String phone = midwifePhoneInput.getText().toString().trim();
        String password = midwifePasswordInput.getText().toString().trim();
        String district = midwifeDistrictSpinner.getSelectedItem().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || district.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Midwife midwife = new Midwife(name, email, phone, password, district);
        db.collection("midwives")
                .add(midwife)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    db.collection("midwives").document(id).update("id", id)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Midwife added successfully", Toast.LENGTH_SHORT).show();
                                clearFields();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Error updating midwife ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding midwife: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        midwifeNameInput.setText("");
        midwifeEmailInput.setText("");
        midwifePhoneInput.setText("");
        midwifePasswordInput.setText("");
        midwifeDistrictSpinner.setSelection(0);
    }
}