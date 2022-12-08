package com.example.mealyapp;

import junit.framework.TestCase;

public class PurchaseRequestTest extends TestCase {

    PurchaseRequest purchaseRequest = new PurchaseRequest("pizza");

    public void testSetMeal() {
        purchaseRequest.setMeal("wings");
        assertEquals(purchaseRequest.getMeal(), "wings");
    }

    public void testGetStatus() {
        assertEquals(purchaseRequest.getStatus(), "pending");
    }

    public void testGetMeal() {
        assertEquals(purchaseRequest.getMeal(), "pizza");
    }

    public void testSetStatus() {
        purchaseRequest.setStatus("approved");
        assertEquals(purchaseRequest.getStatus(), "approved");
    }
}