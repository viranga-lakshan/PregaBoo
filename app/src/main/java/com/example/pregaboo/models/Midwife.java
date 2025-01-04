package com.example.pregaboo.models;

public class Midwife {
    private String name;
    private String email;
    private String phone;
    private String district;

    public Midwife() {
        // Default constructor required for calls to DataSnapshot.getValue(Midwife.class)
    }

    public Midwife(String name, String email, String phone, String district) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDistrict() {
        return district;
    }
}