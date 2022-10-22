package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeCookFragment extends Fragment {
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_welcome_cook, container, false);
        mAuth = FirebaseAuth.getInstance();
        final TextView tV= (TextView) view.findViewById(R.id.textView1);
        if ((mAuth.getCurrentUser().getUid()).equals("40ylmmzjcSby42d0bMxG2ZO8EX12")){
            String a= getString(R.string.wel_admin);
            tV.setText(a);
        }else{
            /*FirebaseUser user = mAuth.getCurrentUser();
            FirebaseDatabase.getInstance().getReference("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User userProfile = snapshot.getValue(User.class);
                    if (userProfile != null){
                        String role= userProfile.role;
                        if (role.equals("Cook")){
                        tV.setText(getString(R.string.wel_cook));
                        }else if (role.equals( "Client")) {
                            tV.setText(getString(R.string.wel_client));
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
            tV.setText(getString(R.string.wel_cook));
        }



        Button logout= (Button) view.findViewById(R.id.logoutID);
        logout.setOnClickListener(new View.OnClickListener() {
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
}