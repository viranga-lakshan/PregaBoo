package com.example.pregaboo.models;

public class Vaccine {
    private String id;
    private String date;
    private String vaccineName;
    private String dosage;

    public Vaccine() {
        // Default constructor required for calls to DataSnapshot.getValue(Vaccine.class)
    }

    public Vaccine(String id, String date, String vaccineName, String dosage) {
        this.id = id;
        this.date = date;
        this.vaccineName = vaccineName;
        this.dosage = dosage;
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

    public String getVaccineName() {
        return vaccineName;
    }

    public String getDosage() {
        return dosage;
    }
} 