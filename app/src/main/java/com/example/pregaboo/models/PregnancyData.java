package com.example.pregaboo.models;

public class PregnancyData {
    private String userId;
    private long dueDate;
    private long lastPeriodDate;
    private String contactNumber;
    private String district;
    private long createdAt;

    public PregnancyData(String userId, long lastPeriodDate, String contactNumber, String district) {
        this.userId = userId;
        this.lastPeriodDate = lastPeriodDate;
        this.dueDate = lastPeriodDate + (280L * 24 * 60 * 60 * 1000);
        this.contactNumber = contactNumber;
        this.district = district;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters
    public String getUserId() { return userId; }
    public long getDueDate() { return dueDate; }
    public long getLastPeriodDate() { return lastPeriodDate; }
    public String getContactNumber() { return contactNumber; }
    public String getDistrict() { return district; }
    public long getCreatedAt() { return createdAt; }
} 