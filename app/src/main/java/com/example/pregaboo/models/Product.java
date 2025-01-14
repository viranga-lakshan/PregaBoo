package com.example.pregaboo.models;

public class Product {
    private String name;
    private String description;
    private String imageBase64;
    private double price;
    private int warrantyPeriod;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Product.class)
    }

    public Product(String name, String description, String imageBase64, double price, int warrantyPeriod) {
        this.name = name;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.price = price;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getWarrantyPeriod() { return warrantyPeriod; }
    public void setWarrantyPeriod(int warrantyPeriod) { this.warrantyPeriod = warrantyPeriod; }
} 