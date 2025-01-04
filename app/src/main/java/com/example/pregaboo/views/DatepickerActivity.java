package com.example.pregaboo.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pregaboo.R;
import com.example.pregaboo.controllers.PregnancyController;
import com.example.pregaboo.models.PregnancyData;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.AlertDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class DatepickerActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private EditText etDateInput;
    private Button btnContinue;
    private PregnancyController pregnancyController;
    private FirebaseAuth mAuth;
    private SimpleDateFormat dateFormat;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

        initializeComponents();
        setupDatePicker();
        setupClickListeners();
    }

    private void initializeComponents() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        pregnancyController = new PregnancyController();
        datePicker = findViewById(R.id.datePicker);
        etDateInput = findViewById(R.id.etDateInput);
        btnContinue = findViewById(R.id.btnContinue);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    private void setupDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -9); // Set to 9 months ago as default
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            (view, year, monthOfYear, dayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
                etDateInput.setText(dateFormat.format(selectedDate.getTime()));
            }
        );
    }

    private void setupClickListeners() {
        String contactNumber = getIntent().getStringExtra("contact_number");
        String district = getIntent().getStringExtra("selected_district");

        btnContinue.setOnClickListener(v -> {
            if (contactNumber != null && district != null && !etDateInput.getText().toString().isEmpty()) {
                // First create/update user document
                String userId = mAuth.getCurrentUser().getUid();
                db.collection("users")
                    .document(userId)
                    .set(new HashMap<>())  // Create empty document if doesn't exist
                    .addOnSuccessListener(aVoid -> savePregnancyData(contactNumber, district))
                    .addOnFailureListener(e -> showError("Failed to initialize user document", e));
            } else {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePregnancyData(String contactNumber, String district) {
        String userId = mAuth.getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        
        PregnancyData pregnancyData = new PregnancyData(
            userId,
            calendar.getTimeInMillis(),
            contactNumber,
            district
        );

        pregnancyController.savePregnancyData(pregnancyData)
            .addOnSuccessListener(aVoid -> {
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            })
            .addOnFailureListener(e -> showError("Failed to save pregnancy data", e));
    }

    private void showError(String message, Exception e) {
        new AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message + "\n\nDetails: " + e.getMessage())
            .setPositiveButton("Retry", (dialog, which) -> {
                String contactNumber = getIntent().getStringExtra("contact_number");
                String district = getIntent().getStringExtra("selected_district");
                savePregnancyData(contactNumber, district);
            })
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .show();
    }
} 