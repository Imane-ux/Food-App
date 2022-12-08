package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class RequestTest extends TestCase {

    Request request = new Request("group28", "pizza");

    @Test
    public void testGetClientUID(){
        assertEquals(request.getClientUID(), "group28");
    }


    public void testSetClientUID() {
        request.setClientUID("group28_v2");
        assertEquals(request.getClientUID(), "group28_v2");
    }

    public void testGetMealName() {
        assertEquals(request.getMealName(), "pizza");
    }

    public void testSetMealName() {
        request.setMealName("wings");
        assertEquals(request.getMealName(), "wings");
    }
}