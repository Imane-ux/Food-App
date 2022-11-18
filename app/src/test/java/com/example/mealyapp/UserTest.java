package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    @Test
    public void testGetRole() {
        User user = new User("Client");
        User user2 = new User("Cook");
        String expected = "Client";
        String expected2 = "Cook";

        assertEquals(expected, user.getRole());
        assertEquals(expected2, user2.getRole());
    }



}