package com.example.pregaboo.models;

public class Reminder {
    private String note;
    private String date;

    public Reminder() {
        // Default constructor required for calls to DataSnapshot.getValue(Reminder.class)
    }

    public Reminder(String note, String date) {
        this.note = note;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}