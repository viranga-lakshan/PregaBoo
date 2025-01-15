package com.example.pregaboo.models;

public class GrowthData {
    private String date;
    private double height;
    private double weight;

    public GrowthData() {
        // Default constructor required for calls to DataSnapshot.getValue(GrowthData.class)
    }

    public GrowthData(String date, double height, double weight) {
        this.date = date;
        this.height = height;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}