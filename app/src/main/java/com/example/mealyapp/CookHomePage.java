package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


public class CookHomePage extends Fragment {

    FirebaseAuth mAuth;
    Cook cook;
    ArrayList<Complaint> complaints;
    int daysOfBanLeft = 0;
    boolean permanentlyBanned = false;
    RelativeLayout mainLayout, bannedLayout;
    TextView bannedText, dummyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook_home_page, container, false);

        mAuth = FirebaseAuth.getInstance();
        complaints = new ArrayList<>();
        mainLayout = view.findViewById(R.id.main_layout);
        bannedLayout = view.findViewById(R.id.banned_layout);
        bannedText = view.findViewById(R.id.banned_text);
        dummyText = view.findViewById(R.id.dummy_text);

        getCookDetails();
        getComplaints();

        // Logout
        Button logoutButton = view.findViewById(R.id.login_button);
        Button logoutButton2 = view.findViewById(R.id.logout_button_2);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        logoutButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return view;
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager= getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new StartFragment()).addToBackStack(null).commit();
    }


    // Retrieving all cook's attributes from firebase;
    @SuppressWarnings("unchecked")
    public void getCookDetails()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
        reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String role = String.valueOf(dataSnapshot.child("role").getValue());
                String password = String.valueOf(dataSnapshot.child("password").getValue());
                String email = String.valueOf(dataSnapshot.child("email").getValue());
                String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
                String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
                String address = String.valueOf(dataSnapshot.child("address").getValue());
                String description = String.valueOf(dataSnapshot.child("description").getValue());
                ArrayList<String> complaintStrings = (ArrayList<String>) dataSnapshot.child("complaints").getValue();

                cook = new Cook(role, password, email, firstName, lastName, address, description, complaintStrings);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Getting all complaints associated with the current cook.
    @SuppressWarnings("unchecked")
    public void getComplaints()
    {
        try {
            for(int i = 0; i < cook.getComplaints().size(); i++) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Complaints");


                reference.child(cook.getComplaints().get(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String cookUID = String.valueOf(dataSnapshot.child("cookUID").getValue());
                        String complaint = String.valueOf(dataSnapshot.child("complaint").getValue());
                        String startOfBan = String.valueOf(dataSnapshot.child("startOfBan").getValue());
                        int daysOfTemporaryBan = Integer.parseInt(String.valueOf(dataSnapshot.child("firstName").getValue()));
                        boolean permanentBan = Boolean.parseBoolean(String.valueOf(dataSnapshot.child("lastName").getValue()));

                        complaints.add(new Complaint(cookUID, complaint, startOfBan, daysOfTemporaryBan, permanentBan));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                checkBan();

            }
        } catch (Exception e) {}

    }

    // Checking if the cook is banned.
    @SuppressWarnings("unchecked")
    public void checkBan()
    {
        if(!permanentlyBanned)
        {
            long maxDays = 0;

            // Looking for a maximum number of days banned.
            for (Complaint complaint: complaints)
            {
                long startTime = Timestamp.parse(complaint.getStartOfBan());
                long currentTime = Timestamp.parse(new Timestamp(new Date().getTime()).toString());
                long temp = currentTime - startTime;

                if(temp > maxDays){ maxDays = temp; }
            }

            // Displayed the banned message if the cook is banned.
            if(maxDays > 0)
            {
                bannedText.append(" for" + maxDays);
                mainLayout.setVisibility(View.INVISIBLE);
                bannedLayout.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            bannedText.append(" permanently!");
            mainLayout.setVisibility(View.INVISIBLE);
            bannedLayout.setVisibility(View.VISIBLE);
        }
    }
}