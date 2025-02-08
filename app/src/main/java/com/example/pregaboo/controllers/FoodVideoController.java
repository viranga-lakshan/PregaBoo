package com.example.pregaboo.controllers;

import com.example.pregaboo.models.Food;
import java.util.ArrayList;
import java.util.List;

public class FoodVideoController {

    public FoodVideoController() {}

    public void getAllFoodVideos(OnCompleteListener<List<Food>> onCompleteListener) {
        List<Food> foodVideos = new ArrayList<>();

        // Add the new video as the first one
        foodVideos.add(new Food("1",
                "Delicious Food Recipe | Easy to Cook",
                "dQw4w9WgXcQ",
                "https://img.youtube.com/vi/dQw4w9WgXcQ/0.jpg",
                "Learn how to cook delicious food with this easy recipe"));

        // Add the new video as the second one
        foodVideos.add(new Food("2",
                "Healthy Food Recipe",
                "eVTXPUF4Oz4",
                "https://img.youtube.com/vi/eVTXPUF4Oz4/0.jpg",
                "Healthy food recipe for a balanced diet"));

        // Keep existing videos
        foodVideos.add(new Food("3",
                "Quick and Easy Breakfast",
                "3JZ_D3ELwOQ",
                "https://img.youtube.com/vi/3JZ_D3ELwOQ/0.jpg",
                "Quick and easy breakfast recipes"));

        foodVideos.add(new Food("4",
                "Vegan Recipes for Beginners",
                "2Vv-BfVoq4g",
                "https://img.youtube.com/vi/2Vv-BfVoq4g/0.jpg",
                "Simple vegan recipes for beginners"));

        foodVideos.add(new Food("5",
                "Gourmet Cooking at Home",
                "kXYiU_JCYtU",
                "https://img.youtube.com/vi/kXYiU_JCYtU/0.jpg",
                "Gourmet cooking techniques for home chefs"));

        onCompleteListener.onComplete(foodVideos);
    }

    public interface OnCompleteListener<T> {
        void onComplete(T result);
        void onError(Exception e);
    }
}