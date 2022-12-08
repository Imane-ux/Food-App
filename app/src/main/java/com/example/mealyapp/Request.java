package com.example.mealyapp;

public class Request {
    String clientUID;
    String mealName;

    public Request() {

    }
    public Request(String mealName) {

    }

    public Request(String clientUID, String mealName) {
        this.clientUID = clientUID;
        this.mealName = mealName;
    }

    public String getClientUID() {
        return clientUID;
    }

    public void setClientUID(String clientUID) {
        this.clientUID = clientUID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
}
