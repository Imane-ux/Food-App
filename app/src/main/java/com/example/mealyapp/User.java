package com.example.mealyapp;

public class User {
    private String role,password,email,firstName,lastName,address;

    public User(String role, String password, String email, String firstName, String lastName, String address) {
        this.role= role;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }

    public User(String role)
    {
        this.role = role;
    }

    public String getRole() { return this.role; }

    public String toString()
    {
        return this.role + ", " + this.firstName + ", " + this.lastName;
    }

}

