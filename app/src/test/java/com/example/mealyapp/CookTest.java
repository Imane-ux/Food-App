package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class CookTest extends TestCase {

    @Test
    public void testIsPermanentlyBanned() {
        Cook cook = new Cook("Client", true);
        boolean expected = true;

        assertEquals(expected, cook.isPermanentlyBanned());
    }

    @Test
    public void testIsNotPermanentlyBanned()
    {
        Cook cook = new Cook("Client", true);
        boolean expected = false;

        assertFalse(expected == cook.isPermanentlyBanned());
    }

}