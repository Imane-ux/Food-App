package com.example.mealyapp;

<<<<<<< HEAD
import java.util.*;

public class Cook extends User {
    public String postalCode,description;
    private List<String> complaints;
=======

public class Cook extends User {
    public String postalCode,description, startOfBan;
    public int daysLeftTemporaryBan;
    public boolean permanentBan;

>>>>>>> dfd0e11b976cae8b6f2355936ca5b8905c1a05b4

    public Cook(String cook, String passwordCook, String emailCook, String firstNameCook, String lastNameCook, String pickupAddress, String postalCode, String description) {
        super(cook, passwordCook, emailCook, firstNameCook, lastNameCook, pickupAddress);
        this.postalCode = postalCode;
        this.description = description;
<<<<<<< HEAD
        this.complaints = new ArrayList<>();
=======
        this.daysLeftTemporaryBan = 0;
        this.permanentBan = false;
        this.startOfBan = null;

>>>>>>> dfd0e11b976cae8b6f2355936ca5b8905c1a05b4
    }
}
