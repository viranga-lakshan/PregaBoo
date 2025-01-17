package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.GrowthDataAdapter;
import com.example.pregaboo.models.GrowthData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class MomBmiChart extends AppCompatActivity {
    private EditText heightInput, weightInput, milestonesInput;
    private Button saveButton;
    private RecyclerView growthRecyclerView;
    private GrowthDataAdapter adapter;
    private List<GrowthData> growthDataList;
    private FirebaseFirestore db;
    private String userId;
    private LineChart bmiChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_bmi_chart);

        db = FirebaseFirestore.getInstance();
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        milestonesInput = findViewById(R.id.milestonesInput);
        saveButton = findViewById(R.id.saveButton);
        growthRecyclerView = findViewById(R.id.growthRecyclerView);
        bmiChart = findViewById(R.id.bmiChart);

        growthDataList = new ArrayList<>();
        adapter = new GrowthDataAdapter(growthDataList);
        growthRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        growthRecyclerView.setAdapter(adapter);

        userId = getIntent().getStringExtra("USER_ID");

        if (userId == null) {
            Toast.makeText(this, "Error: User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        saveButton.setOnClickListener(v -> saveGrowthData());
        loadGrowthData();
    }

    private void saveGrowthData() {
        String heightStr = heightInput.getText().toString().trim();
        String weightStr = weightInput.getText().toString().trim();
        String milestones = milestonesInput.getText().toString().trim();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr) / 100; // Convert cm to meters
        double weight = Double.parseDouble(weightStr);

        // Calculate BMI
        double bmi = weight / (height * height);

        // Create a map to store growth data
        Map<String, Object> growthData = new HashMap<>();
        growthData.put("date", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        growthData.put("height", height);
        growthData.put("weight", weight);
        growthData.put("milestones", milestones);
        growthData.put("bmi", bmi);

        db.collection("users")
                .document(userId)
                .collection("growth")
                .add(growthData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MomBmiChart.this, "Growth data saved successfully", Toast.LENGTH_SHORT).show();
                    heightInput.setText("");
                    weightInput.setText("");
                    milestonesInput.setText("");
                    loadGrowthData(); // Refresh the RecyclerView
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MomBmiChart.this, "Error saving growth data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadGrowthData() {
        db.collection("users")
                .document(userId)
                .collection("growth")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        growthDataList.clear();
                        List<Entry> bmiEntries = new ArrayList<>();
                        int index = 0; // For x-axis

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            GrowthData growthData = document.toObject(GrowthData.class);
                            growthDataList.add(growthData);

                            // Assuming growthData has a method getBmi() to get the BMI value
                            double bmi = growthData.getBmi();
                            bmiEntries.add(new Entry(index++, (float) bmi)); // Add entry for chart
                        }

                        // Set up the chart
                        LineDataSet lineDataSet = new LineDataSet(bmiEntries, "BMI Over Time");
                        LineData lineData = new LineData(lineDataSet);
                        bmiChart.setData(lineData);
                        bmiChart.invalidate(); // Refresh the chart
                    } else {
                        Toast.makeText(MomBmiChart.this, "Error loading growth data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}