package com.example.mealyapp;

public class Complaint {
    private String cookUID, complaint, startOfBan;
    private int daysOfTemporaryBan;
    private boolean permanentBan;

    public Complaint() {

    }

    public Complaint(String complaint){
        this.complaint = complaint;
    }

    public Complaint(String cookUID, String complaint, String startOfBan, int dayOfTemporaryBan, boolean permanentBan) {
        this.cookUID = cookUID;
        this.complaint = complaint;
        this.startOfBan = startOfBan;
        this.daysOfTemporaryBan = dayOfTemporaryBan;
        this.permanentBan = permanentBan;
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

    public String getStartOfBan() { return startOfBan; }

    public void setStartOfBan (String startOfBan) { this.startOfBan = startOfBan; }

    public int getDaysOfTemporaryBan() { return daysOfTemporaryBan; }

    public void setDaysOfTemporaryBan (int daysOfTemporaryBan) { this.daysOfTemporaryBan = daysOfTemporaryBan; }

    public boolean getPermanentBan() { return permanentBan; }

    public void setPermanentBan (boolean permanentBan) { this.permanentBan = permanentBan; }
}
