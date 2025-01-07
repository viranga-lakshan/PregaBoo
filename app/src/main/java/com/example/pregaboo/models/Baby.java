package com.example.pregaboo.models;

import com.google.firebase.firestore.PropertyName;

public class Baby {
    private String babyId;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String deliveryMethod;
    private String userId; // To connect to the user

    public Baby() {
        // Default constructor required for calls to DataSnapshot.getValue(Baby.class)
    }

    public Baby(String babyId, String name, String gender, String dateOfBirth, String deliveryMethod, String userId) {
        this.babyId = babyId;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.deliveryMethod = deliveryMethod;
        this.userId = userId;
    }

    @PropertyName("id")
    public String getBabyId() {
        return babyId;
    }

    @PropertyName("id")
    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
} 