package com.example.mealyapp;

import junit.framework.TestCase;

import org.junit.Test;

public class ComplaintTest extends TestCase {

    @Test
    public void testGetComplaint() {
        Complaint complaint = new Complaint("Bad");
        String expected = "Bad";

        assertEquals(expected, complaint.getComplaint());
    }

}