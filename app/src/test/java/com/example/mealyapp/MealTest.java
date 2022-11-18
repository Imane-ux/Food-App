package com.example.mealyapp;

import junit.framework.TestCase;

public class MealTest extends TestCase {

    Meal meal = new Meal("a", "a", "a", "a", "a", "a");

    public void testSetType() {
        meal.setType("Italian");
        assertEquals("Italian", meal.getType());
    }

    public void testGetAllergens() {
        assertEquals("a", meal.getAllergens());
    }
}