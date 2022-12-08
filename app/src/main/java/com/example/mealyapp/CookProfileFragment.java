package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CookProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    TextView firstNameText, lastNameText, emailText, addressText, descriptionText, ratingText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        firstNameText = view.findViewById(R.id.first_name_text);
        lastNameText = view.findViewById(R.id.last_name_text);
        emailText = view.findViewById(R.id.email_text);
        addressText = view.findViewById(R.id.address_text);
        descriptionText = view.findViewById(R.id.description_text);
        ratingText = view.findViewById(R.id.rating_text);

        cookDetails(mAuth.getCurrentUser().getUid());

        return view;
    }

    public void cookDetails(String cOOKuid){
        DatabaseReference dr;
        dr = FirebaseDatabase.getInstance().getReference("user").child(cOOKuid);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = String.valueOf(snapshot.child("firstName").getValue());
                String lastName = String.valueOf(snapshot.child("lastName").getValue());
                String email = String.valueOf(snapshot.child("email").getValue());
                String address = String.valueOf(snapshot.child("address").getValue());
                String description = String.valueOf(snapshot.child("description").getValue());
                String rating = String.valueOf(snapshot.child("rating").getValue());

                setTexts(firstName, lastName, email, address, description, rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setTexts(String firstName, String lastName, String email, String address, String description, String rating){
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        emailText.setText(email);
        addressText.setText(address);
        descriptionText.setText(description);

        try {
            if (Double.parseDouble(rating) > 0.0) {
                ratingText.setText(rating);
            }
        } catch(Exception e) {}
    }
}