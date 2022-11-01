package com.example.mealyapp;

public class Complaint {
    private String cookUID, complaint;

    public Complaint(String cookUID, String complaint) {
        this.cookUID = cookUID;
        this.complaint = complaint;
    }

    public String getCookUID() {
        return cookUID;
    }

    public void setCookUID(String cookUID) {
        this.cookUID = cookUID;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
