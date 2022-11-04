package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Cook_Home_Page extends Fragment {

    FirebaseAuth mAuth;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook__home__page, container, false);

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    public void getCookDetails()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    DataSnapshot dataSnapshot = task.getResult();
                    String role = String.valueOf(dataSnapshot.child("role").getValue());
                    String password = String.valueOf(dataSnapshot.child("role").getValue());
                    String email = String.valueOf(dataSnapshot.child("role").getValue());
                    String firstName = String.valueOf(dataSnapshot.child("role").getValue());
                    String lastName = String.valueOf(dataSnapshot.child("role").getValue());
                    String address = String.valueOf(dataSnapshot.child("role").getValue());
                    user = new User(role, password, email, firstName, lastName, address);
                }
            }
        });
    }
}