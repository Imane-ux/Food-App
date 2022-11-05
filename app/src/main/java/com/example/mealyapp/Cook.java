package com.example.mealyapp;


import java.util.*;

public class Cook extends User {
    private String description, startOfBan;
    private int daysOfTemporaryBan;
    private boolean permanentBan;
    private ArrayList<String> complaints;

    public Cook(String role, String password, String email, String firstName, String lastName, String address, String description, ArrayList<String> complaints) {
        super(role, password, email, firstName, lastName, address);
        this.description = description;
        this.complaints = complaints;
        this.daysOfTemporaryBan = 0;
        this.permanentBan = false;
        this.startOfBan = null;
    }

    public Cook(String role, boolean permanentBan)
    {
        super(role);
        this.permanentBan = permanentBan;
    }

    public boolean isPermanentlyBanned() { return permanentBan; }

    public ArrayList<String> getComplaints() {
        return complaints;
    }

    public String toString() { return this.complaints.size() + " "; }
}
