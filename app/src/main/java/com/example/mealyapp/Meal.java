package com.example.mealyapp;

public class Meal {
    private String name, type, cuisine, ingredients, allergens,price, description, cookId;

    public Meal(){

    }
    public Meal(String name, String type, String cuisine, String ingredients, String allergens, String price, String description, String cookId) {
        this.name= name;
        this.type=type;
        this.cuisine=cuisine;
        this.ingredients=ingredients;
        this.allergens=allergens;
        this.price= price;
        this.description=description;
        this.cookId= cookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
