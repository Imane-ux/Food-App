package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeClientFragment extends Fragment {
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_welcome_client, container, false);
        mAuth = FirebaseAuth.getInstance();
        final TextView tV= (TextView) view.findViewById(R.id.textView1);
        if ((mAuth.getCurrentUser().getUid()).equals("40ylmmzjcSby42d0bMxG2ZO8EX12")){
            String a= getString(R.string.wel_admin);
            tV.setText(a);

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new AdminFragment()).commit();
                }
            };

            timer.schedule(timerTask, 1500);

        }else{
            tV.setText(getString(R.string.wel_client));
        }

        Button logout2= (Button) view.findViewById(R.id.logoutID2);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager= getParentFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, new StartFragment()).addToBackStack(null).commit();
            }
        });

        return view;

    }

    // Not in use yet... still testing.
    public void decider()
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
                    User user = new User(role, password, email, firstName, lastName, address);
                }
            }
        });
    }
}