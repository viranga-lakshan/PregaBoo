package com.example.pregaboo.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.ExerciseAdapter;
import com.example.pregaboo.controllers.ExerciseController;
import com.example.pregaboo.models.Exercise;
import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private ExerciseController exerciseController;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        recyclerView = findViewById(R.id.exerciseRecyclerView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        
        exerciseController = new ExerciseController();
        loadExerciseVideos();
    }

    private void loadExerciseVideos() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        exerciseController.getAllExercises(new ExerciseController.OnCompleteListener<List<Exercise>>() {
            @Override
            public void onComplete(List<Exercise> exercises) {
                loadingProgressBar.setVisibility(View.GONE);
                adapter = new ExerciseAdapter(exercises, ExerciseActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(ExerciseActivity.this, 
                    "Error loading videos: " + e.getMessage(), 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
} 