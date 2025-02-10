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
                "yhYtM1wNlrA",
                "https://img.youtube.com/vi/yhYtM1wNlrA/0.jpg",
                "Learn how to cook delicious food with this easy recipe"));


        // Add the new video as the second one
        foodVideos.add(new Food("2",
                "Another Delicious Recipe | Must Try",
                "BlQ8_B1XMLs",
                "https://img.youtube.com/vi/BlQ8_B1XMLs/0.jpg",
                "Try this amazing recipe that is both easy and delicious!"));


        // Keep existing videos
        foodVideos.add(new Food("4",
                "Easy & Delicious Meal | Quick to Prepare",
                "-flYzSZr1kE",
                "https://img.youtube.com/vi/-flYzSZr1kE/0.jpg",
                "Discover this easy and delicious meal you can prepare in no time!"));


        foodVideos.add(new Food("5",
                "Healthy & Tasty Recipe | Quick and Easy",
                "-Wp08eaXd-g",
                "https://img.youtube.com/vi/-Wp08eaXd-g/0.jpg",
                "Try this healthy and tasty recipe that's quick and easy to make!"));




        onCompleteListener.onComplete(foodVideos);
    }

    public interface OnCompleteListener<T> {
        void onComplete(T result);
        void onError(Exception e);
    }
}