package com.example.pregaboo.controllers;

import android.content.Context;
import com.example.pregaboo.models.Exercise;
import java.util.ArrayList;
import java.util.List;

public class ExerciseController {

    public ExerciseController() {}

    public void getAllExercises(OnCompleteListener<List<Exercise>> onCompleteListener) {
        List<Exercise> exercises = new ArrayList<>();

        // Add the new video as the first one
        exercises.add(new Exercise("1",
                "Pregnancy Workout | Safe Exercises All Trimesters",
                "lKx0sOz31C4",
                "https://img.youtube.com/vi/lKx0sOz31C4/0.jpg",
                "Complete pregnancy workout suitable for all trimesters"));

        // Add the new video as the second one
        exercises.add(new Exercise("2",
                "New Pregnancy Exercise Video",
                "8pOJSR74ZoY",
                "https://img.youtube.com/vi/8pOJSR74ZoY/0.jpg",
                "New pregnancy exercise video"));

        // Keep existing videos
        exercises.add(new Exercise("3",
                "Pregnancy Safe Full Body Workout",
                "H5tSPUqkYqE",
                "https://img.youtube.com/vi/H5tSPUqkYqE/0.jpg",
                "30-minute pregnancy workout suitable for all trimesters"));

        exercises.add(new Exercise("4",
                "Prenatal Yoga For All Trimesters",
                "0cKnStmV1rE",
                "https://img.youtube.com/vi/0cKnStmV1rE/0.jpg",
                "Gentle yoga routine for pregnant women"));

        exercises.add(new Exercise("5",
                "First Trimester Pregnancy Exercises",
                "7tW6lkqRcHY",
                "https://img.youtube.com/vi/7tW6lkqRcHY/0.jpg",
                "Safe exercises for first trimester"));

        onCompleteListener.onComplete(exercises);
    }

    public interface OnCompleteListener<T> {
        void onComplete(T result);
        void onError(Exception e);
    }
}