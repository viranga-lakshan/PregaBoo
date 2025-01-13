package com.example.pregaboo.models;

import com.google.firebase.firestore.PropertyName;
import com.google.firebase.Timestamp;

public class Post {
    private String text;
    private String imageBase64;
    private String videoUrl;
    private int likes;
    private Timestamp timestamp;

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    @PropertyName("timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @PropertyName("timestamp")
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}