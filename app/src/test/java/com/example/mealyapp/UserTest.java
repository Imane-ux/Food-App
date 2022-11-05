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

//    @Test
//    public void testGetUserDetails() {
//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
//        reference.child("YyEhDIBj2HahwSI3K0gHGKORJu63").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String role = String.valueOf(dataSnapshot.child("role").getValue());
//                String password = String.valueOf(dataSnapshot.child("role").getValue());
//                String email = String.valueOf(dataSnapshot.child("role").getValue());
//                String firstName = String.valueOf(dataSnapshot.child("role").getValue());
//                String lastName = String.valueOf(dataSnapshot.child("role").getValue());
//                String address = String.valueOf(dataSnapshot.child("role").getValue());
//                User user = new User(role, password, email, firstName, lastName, address);
//
//                assertEquals("Cook", user.getRole());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}