package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.controllers.WeightController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.adapters.WeightJournalAdapter;
import com.example.pregaboo.models.Weight;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowMotherWeight extends AppCompatActivity {
    private Spinner weekSpinner;
    private EditText weightInput;
    private Button addWeightButton;
    private WeightController weightController;
    private RecyclerView journalRecyclerView;
    private WeightJournalAdapter adapter;
    private List<Weight> weightList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mother_weight);

        weightController = new WeightController();
        initializeViews();
        setupWeekSpinner();
        setupClickListeners();
        loadWeights();
    }

    private void initializeViews() {
        weekSpinner = findViewById(R.id.weekSpinner);
        weightInput = findViewById(R.id.weightInput);
        addWeightButton = findViewById(R.id.addWeightButton);
        journalRecyclerView = findViewById(R.id.journalRecyclerView);
        journalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeightJournalAdapter(weightList);
        journalRecyclerView.setAdapter(adapter);
    }

    private void setupWeekSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.weeks_array,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(adapter);
    }

    private void setupClickListeners() {
        addWeightButton.setOnClickListener(v -> saveWeight());
    }

    private void saveWeight() {
        String weightStr = weightInput.getText().toString().trim();
        if (weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter weight", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            int weekPosition = weekSpinner.getSelectedItemPosition() + 1; // +1 because array is 0-based

            weightController.saveWeight(weekPosition, weight)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Weight saved successfully", Toast.LENGTH_SHORT).show();
                    weightInput.setText("");
                    loadWeights();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to save weight: " + e.getMessage(), 
                                 Toast.LENGTH_SHORT).show();
                });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadWeights() {
        weightController.getWeights()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                weightList.clear();
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    Weight weight = document.toObject(Weight.class);
                    if (weight != null) {
                        weightList.add(weight);
                    }
                }
                adapter.updateWeights(weightList);
            })
            .addOnFailureListener(e -> 
                Toast.makeText(this, "Failed to load weights", Toast.LENGTH_SHORT).show()
            );
    }
}