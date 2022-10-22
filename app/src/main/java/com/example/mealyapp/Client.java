package com.example.mealyapp;

public class Client extends User{
    public String cardNumber,expiryYear,expiryMonth,securityCode,nameOnCard;

    public Client(String role, String password, String email, String firstName, String lastName, String address, String cardNumber, String expiryYear, String expiryMonth, String securityCode, String nameOnCard){
        super(role, password, email, firstName, lastName, address);
        this.cardNumber = cardNumber;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        this.securityCode = securityCode;
        this.nameOnCard = nameOnCard;
    }
}
