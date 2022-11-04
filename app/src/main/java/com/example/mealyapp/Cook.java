package com.example.mealyapp;


public class Cook extends User {
    public String postalCode,description, startOfBan;
    public int daysLeftTemporaryBan;
    public boolean permanentBan;


    public Cook(String cook, String passwordCook, String emailCook, String firstNameCook, String lastNameCook, String pickupAddress, String postalCode, String description) {
        super(cook, passwordCook, emailCook, firstNameCook, lastNameCook, pickupAddress);
        this.postalCode = postalCode;
        this.description = description;
        this.daysLeftTemporaryBan = 0;
        this.permanentBan = false;
        this.startOfBan = null;

    }
}
