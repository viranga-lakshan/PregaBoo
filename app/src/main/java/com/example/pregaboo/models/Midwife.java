package com.example.pregaboo.models;

public class Midwife {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String location;

    public Midwife() {
        // Default constructor required for calls to DataSnapshot.getValue(Midwife.class)
    }

    public Midwife(String name, String email, String phone, String password, String location) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.location = location;
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

    public String getPassword() {
        return password;
    }

    public String getlocation() {
        return location;
    }
}