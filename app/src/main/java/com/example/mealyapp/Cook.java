package com.example.mealyapp;


import java.util.*;

public class Cook extends User {
    private String description, startOfBan;
    private int daysOfTemporaryBan;
    private boolean permanentBan, banned;
    private ArrayList<String> complaints;
    private double rating, ratingCount;

    public Cook(String role, String password, String email, String firstName, String lastName, String address, String description, ArrayList<String> complaints) {
        super(role, password, email, firstName, lastName, address);
        this.description = description;
        this.complaints = complaints;
        this.daysOfTemporaryBan = 0;
        this.banned=false;
        this.permanentBan = false;
        this.startOfBan = null;
        this.rating = 0.0;
        this.ratingCount = 0.0;
    }

    public Cook(){ this.complaints = new ArrayList<>(); }

    public Cook(String role, boolean permanentBan)
    {
        super(role);
        this.permanentBan = permanentBan;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getDescription() {
        return description;
    }

    public String getStartOfBan() {
        return startOfBan;
    }

    public int getDaysOfTemporaryBan() {
        return daysOfTemporaryBan;
    }

    public boolean isPermanentBan() {
        return permanentBan;
    }

    public ArrayList<String> getComplaints() {
        return complaints;
    }

    public int getNumberOfComplaints() { return this.complaints.size(); }

    public void addComplaint(String complaint) { this.complaints.add(complaint); }

    public boolean isPermanentlyBanned() { return permanentBan; }

    public void rate(double newRating){
        rating = ((rating * ratingCount) + newRating)/(ratingCount+1);
        ratingCount++;
    }
}
