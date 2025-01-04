package com.example.pregaboo.models;

public class Weight {
    private int week;
    private double weight;

    public Weight(int week, double weight) {
        this.week = week;
        this.weight = weight;
    }

    // Required empty constructor for Firestore
    public Weight() {}

    public int getWeek() { return week; }
    public void setWeek(int week) { this.week = week; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
} 