package com.example.pregaboo.models;

public class Mom {
    private String momId;
    private String name;
    private Long pregnancyDate; // Changed from String to Long
    private String location;

    public Mom() {
        // Default constructor required for calls to DataSnapshot.getValue(Mom.class)
    }

    public Mom(String momId, String name, Long pregnancyDate) { // Changed parameter type to Long
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

    public Long getPregnancyDate() { // Changed return type to Long
        return pregnancyDate;
    }

   

 

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}






