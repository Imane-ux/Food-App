package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends Fragment {

    private FirebaseAuth mAuth;
    private User user;
    String userRole;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_welcome, container, false);
        mAuth = FirebaseAuth.getInstance();

        getUserDetails(view);


//
//        Button logout2= (Button) view.findViewById(R.id.logoutID2);
//        logout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
//                FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.fragmentContainer, new StartFragment()).addToBackStack(null).commit();
//            }
//        });

        return view;

    }

    public void getUserDetails(View view) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String role = String.valueOf(dataSnapshot.child("role").getValue());

                TextView tV = (TextView) view.findViewById(R.id.welcome_text);

                if ((mAuth.getCurrentUser().getUid()).equals("19NivPZXZmbn7P7OKjtzc3MUSl53")){
                    tV.append(" Admin!");

                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, new AdminFragment()).commit();
                        }
                    };

                    timer.schedule(timerTask, 1500);

                }
                else
                {
                    tV.append(" " + role);

                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentContainer, new CookHomePage()).commit();
                        }
                    };

                    timer.schedule(timerTask, 1500);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}