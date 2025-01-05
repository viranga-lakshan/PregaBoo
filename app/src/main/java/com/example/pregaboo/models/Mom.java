package com.example.pregaboo.models;

public class Mom {
    private String momId;
    private String name;
    private String pregnancyDate;

    public Mom() {
        // Default constructor required for calls to DataSnapshot.getValue(Mom.class)
    }

    public Mom(String momId, String name, String pregnancyDate) {
        this.momId = momId;
        this.name = name;
        this.pregnancyDate = pregnancyDate;
    }

    public String getMomId() {
        return momId;
    }

    public String getName() {
        return name;
    }

    public String getPregnancyDate() {
        return pregnancyDate;
    }
}