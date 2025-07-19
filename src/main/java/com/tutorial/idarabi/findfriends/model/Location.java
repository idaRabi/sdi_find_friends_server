package com.tutorial.idarabi.findfriends.model;

public class Location {
    private double latitude;
    private double longitude;
    private String address;
    private java.time.LocalDateTime lastUpdated;

    public Location() {}

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = java.time.LocalDateTime.now();
    }

    public Location(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.lastUpdated = java.time.LocalDateTime.now();
    }

    // Getters and Setters
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public java.time.LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(java.time.LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
