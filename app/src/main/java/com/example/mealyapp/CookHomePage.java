package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;


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

        // Logout
//        Button logoutButton = view.findViewById(R.id.login_button);
//        Button logoutButton2 = view.findViewById(R.id.logout_button_2);

//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });
//
//        logoutButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout();
//            }
//        });

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
    public void getCookDetails()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getCookData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getCookData(DataSnapshot dataSnapshot)
    {
        String role = String.valueOf(dataSnapshot.child("role").getValue());
        String password = String.valueOf(dataSnapshot.child("password").getValue());
        String email = String.valueOf(dataSnapshot.child("email").getValue());
        String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
        String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
        String address = String.valueOf(dataSnapshot.child("address").getValue());
        String description = String.valueOf(dataSnapshot.child("description").getValue());
        ArrayList<String> complaintStrings = new ArrayList<>();

        for (DataSnapshot snapshot: dataSnapshot.child("complaints").getChildren()) {
            complaintStrings.add(snapshot.getValue().toString());
        }

//        Toast.makeText(getActivity(), complaintStrings.size() + " ", Toast.LENGTH_SHORT).show();
        cook = new Cook(role, password, email, firstName, lastName, address, description, complaintStrings);

        getComplaints(cook);

    }

    // Getting all complaints associated with the current cook.
    public void getComplaints(Cook cook)
    {
        for(int i = 0; i < cook.getComplaints().size(); i++) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Complaints");
            reference.child(cook.getComplaints().get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Toast.makeText(getActivity(), cook.getComplaints().size() + " ", Toast.LENGTH_LONG).show();
                    getComplaintsData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void getComplaintsData(DataSnapshot dataSnapshot)
    {
        String cookUID = String.valueOf(dataSnapshot.child("cookUID").getValue());
        String complaint = String.valueOf(dataSnapshot.child("complaint").getValue());
//        String startOfBan = String.valueOf(dataSnapshot.child("startOfBan").getValue());
//        String days = String.valueOf(dataSnapshot.child("daysOfTemporaryBan").getValue());
//        String permanent = String.valueOf(dataSnapshot.child("permanentBan").getValue());
        int daysOfTemporaryBan = 0;
        boolean permanentBan = false;

        if(String.valueOf(dataSnapshot.child("daysOfTemporaryBan").getValue()).equals("15"))
        {
            daysOfTemporaryBan = 15;
            daysOfBanLeft = 15;
        }
        if(String.valueOf(dataSnapshot.child("permanentBan").getValue()).equals("true"))
        {
            permanentBan = true;
            permanentlyBanned = true;
        }

        complaints.add(new Complaint(cookUID, complaint, null, daysOfTemporaryBan, permanentBan));

        checkBan(complaints);
    }

    // Checking if the cook is banned.
    public void checkBan(ArrayList<Complaint> complaints)
    {
        if(!complaints.get(0).getPermanentBan())
        {
            bannedText.append(" for" + complaints.get(0).getDaysOfTemporaryBan());
            mainLayout.setVisibility(View.INVISIBLE);
            bannedLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            bannedText.append(" permanently!");
            mainLayout.setVisibility(View.INVISIBLE);
            bannedLayout.setVisibility(View.VISIBLE);
        }
    }
}