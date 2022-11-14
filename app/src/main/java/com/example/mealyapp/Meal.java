package com.example.mealyapp;

public class Meal {
    private String name, type, cuisine, ingredients, allergens, description;

    public Meal(){

    }
    public Meal(String name, String type, String cuisine, String ingredients, String allergens, String description) {
        this.name= name;
        this.type=type;
        this.cuisine=cuisine;
        this.ingredients=ingredients;
        this.allergens=allergens;
        this.description=description;
    }

    public String getName() {
        return name;
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
