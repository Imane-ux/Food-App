package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    TextView bannedText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook__home__page, container, false);

        mAuth = FirebaseAuth.getInstance();
        mainLayout = view.findViewById(R.id.main_layout);
        bannedLayout = view.findViewById(R.id.banned_layout);
        bannedText = view.findViewById(R.id.banned_text);

        return view;
    }

    // Checking if the cook is banned.
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

    // Retrieving all cook's attributes from firebase;
    public Cook getCookDetails()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists())
                {
                    DataSnapshot dataSnapshot = task.getResult();
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
            }
        });

        return cook;
    }

    // Getting all complaints associated with the current cook.
    public ArrayList<Complaint> getComplaints()
    {
        int i = 0;
        while(i < cook.getComplaints().size()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Complaints");
            reference.child(cook.getComplaints().get(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String cookUID = String.valueOf(dataSnapshot.child("cookUID").getValue());
                        String complaint = String.valueOf(dataSnapshot.child("complaint").getValue());
                        String startOfBan = String.valueOf(dataSnapshot.child("startOfBan").getValue());
                        int daysOfTemporaryBan = Integer.parseInt(String.valueOf(dataSnapshot.child("firstName").getValue()));
                        boolean permanentBan = Boolean.parseBoolean(String.valueOf(dataSnapshot.child("lastName").getValue()));

                        complaints.add(new Complaint(cookUID, complaint, startOfBan, daysOfTemporaryBan, permanentBan));
                    }
                }
            });

            i++;
        }

        return complaints;
    }
}