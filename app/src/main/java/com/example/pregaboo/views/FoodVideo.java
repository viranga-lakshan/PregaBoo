package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.FoodVideoAdapter;
import com.example.pregaboo.controllers.FoodVideoController;
import com.example.pregaboo.models.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodVideo extends AppCompatActivity {
    private RecyclerView foodVideosRecyclerView;
    private FoodVideoAdapter foodVideoAdapter;
    private List<Food> foodVideoList;
    private FoodVideoController foodVideoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_video);

        foodVideosRecyclerView = findViewById(R.id.foodVideosRecyclerView);
        foodVideoList = new ArrayList<>();
        foodVideoAdapter = new FoodVideoAdapter(foodVideoList, this);
        foodVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodVideosRecyclerView.setAdapter(foodVideoAdapter);

        foodVideoController = new FoodVideoController();
        loadFoodVideos();
    }

    private void loadFoodVideos() {
        foodVideoController.getAllFoodVideos(new FoodVideoController.OnCompleteListener<List<Food>>() {
            @Override
            public void onComplete(List<Food> result) {
                foodVideoList.clear();
                foodVideoList.addAll(result);
                foodVideoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(FoodVideo.this, "Error loading food videos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}