package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class ClientTest extends TestCase {

    @Test
    public void testGetRole() {
        User client = new Client("Client");
        String expected = "Client";

        assertEquals(expected, client.getRole());
    }

}