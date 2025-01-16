package com.example.pregaboo.models;

public class ClinicDate {
    private String id;
    private String date;
    private String time;
    private String purpose;

    public ClinicDate() {
        // Default constructor required for calls to DataSnapshot.getValue(ClinicDate.class)
    }

    public ClinicDate(String id, String date, String time, String purpose) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.purpose = purpose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
} 