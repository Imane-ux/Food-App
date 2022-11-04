package com.example.mealyapp;


import java.util.*;

public class Cook extends User {
    private String postalCode,description, startOfBan;
    private int daysLeftTemporaryBan;
    private boolean permanentBan;
    private List<String> complaints;

    public Cook(String cook, String passwordCook, String emailCook, String firstNameCook, String lastNameCook, String pickupAddress, String postalCode, String description) {
        super(cook, passwordCook, emailCook, firstNameCook, lastNameCook, pickupAddress);
        this.postalCode = postalCode;
        this.description = description;
        this.complaints = new ArrayList<>();
        this.daysLeftTemporaryBan = 0;
        this.permanentBan = false;
        this.startOfBan = null;
    }
}
