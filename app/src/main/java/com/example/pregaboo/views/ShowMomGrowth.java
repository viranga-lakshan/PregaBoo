package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.GrowthDataAdapter;
import com.example.pregaboo.models.GrowthData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;

public class ShowMomGrowth extends AppCompatActivity {
    private RecyclerView growthRecyclerView;
    private GrowthDataAdapter adapter;
    private List<GrowthData> growthDataList;
    private FirebaseFirestore db;
    private String userId;
    private LineChart bmiChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mom_growth);

        db = FirebaseFirestore.getInstance();
        growthRecyclerView = findViewById(R.id.growthRecyclerView);
        bmiChart = findViewById(R.id.bmiChart);

        growthDataList = new ArrayList<>();
        adapter = new GrowthDataAdapter(growthDataList);
        growthRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        growthRecyclerView.setAdapter(adapter);

        userId = getIntent().getStringExtra("MOM_ID");

        if (userId == null) {
            Toast.makeText(this, "Error: User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadGrowthData();
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

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShowMomGrowth.this, "Error loading growth data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}