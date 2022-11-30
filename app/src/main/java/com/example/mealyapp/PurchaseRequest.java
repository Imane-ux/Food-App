package com.example.mealyapp;

public class PurchaseRequest {
    String status;
    String meal;

    public PurchaseRequest(String mealName) {

        this.status= "pending";
        this.meal=mealName;
    }
    

    public String getStatus() {
        return status;
    }

    public String getMeal() {
        return meal;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
