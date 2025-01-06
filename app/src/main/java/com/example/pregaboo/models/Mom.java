package com.example.pregaboo.models;

import com.google.firebase.firestore.PropertyName;

public class Mom {
    private String momId;
    private String name;
    private Long pregnancyDate;
    private String location;

    public Mom() {
        // Default constructor required for calls to DataSnapshot.getValue(Mom.class)
    }

    public Mom(String momId, String name, Long pregnancyDate) {
        this.momId = momId;
        this.name = name;
        this.pregnancyDate = pregnancyDate;
    }

    @PropertyName("id")
    public String getMomId() {
        return momId;
    }

    @PropertyName("id")
    public void setMomId(String momId) {
        this.momId = momId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPregnancyDate() {
        return pregnancyDate;
    }

    public void setPregnancyDate(Long pregnancyDate) {
        this.pregnancyDate = pregnancyDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}