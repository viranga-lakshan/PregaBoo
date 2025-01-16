package com.example.pregaboo.models;

public class Vaccine {
    private String id;
    private String vaccineName;
    private String dosage;
    private String date;

    public Vaccine() {
        // Default constructor required for calls to DataSnapshot.getValue(Vaccine.class)
    }

    public Vaccine(String id, String vaccineName, String dosage, String date) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.dosage = dosage;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
} 