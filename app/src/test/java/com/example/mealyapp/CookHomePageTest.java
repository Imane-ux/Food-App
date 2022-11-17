package com.example.mealyapp;

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;


public class CookHomePageTest extends TestCase {

    @Mock
    Context context;


    @Test
    public void testGetCookDetails() {

        CookHomePage cookHomePage = new CookHomePage(context);

        cookHomePage.getCookDetails("Lfc6R3uBUPPthL5oOMWRWHYZ6E83");
        Cook cook = cookHomePage.cook;

        assertEquals(cook.getFirstName(), "sixth");
    }
}