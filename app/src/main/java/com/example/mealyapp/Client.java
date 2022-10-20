package com.example.mealyapp;

public class Client {
    public String password,email,firstName,lastName,address,cardNumber,expiryYear,expiryMonth,securityCode,nameOnCard;

    public Client(String password, String email, String firstName, String lastName, String address, String cardNumber, String expiryYear, String expiryMonth, String securityCode, String nameOnCard) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.cardNumber = cardNumber;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        this.securityCode = securityCode;
        this.nameOnCard = nameOnCard;
    }

    public Client(String password, String email) {
        this.password = password;
        this.email = email;
    }
}

