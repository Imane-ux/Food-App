package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class CookTest extends TestCase {

    @Test
    public void testIsPermanentlyBanned() {
        Cook cook = new Cook("Cook", true);
        boolean expected = true;

        assertEquals(expected, cook.isPermanentlyBanned());
    }

    @Test
    public void testIsNotPermanentlyBanned()
    {
        Cook cook = new Cook("Cook", true);
        boolean expected = false;

        assertFalse(expected == cook.isPermanentlyBanned());
    }

    @Test
    public void testGetComplaints()
    {
        Cook cook = new Cook();
        cook.addComplaint("bad");
        cook.addComplaint("cold");
        cook.addComplaint("rude");

        String allComplaints = "";

        for (String complaint : cook.getComplaints()) {
            allComplaints += complaint;
        }

        assertEquals("badcoldrude", allComplaints);
    }

    @Test
    public void testNumberOfComplaints()
    {
        Cook cook = new Cook();
        cook.addComplaint("bad");
        cook.addComplaint("cold");
        cook.addComplaint("rude");

        assertEquals(3, cook.getNumberOfComplaints());
    }

}