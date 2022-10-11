package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegCookFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_admin, container, false);
        mAuth = FirebaseAuth.getInstance();

        //put your code here. for name, address... again check if the credentials are empty if they are display toasts for it.
        // check password length... if all is good register him using the method below(u'd need to complete it).

        return view;

    }
    private void registerUser(String txt1, String txt2) {
        mAuth.createUserWithEmailAndPassword(txt1,txt2).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "You've registered!", Toast.LENGTH_SHORT).show();
                    // code to start a new fragment here
                }else{
                    Toast.makeText(getActivity(), "Registration failed.", Toast.LENGTH_SHORT).show();
                }}
        });
    }
}