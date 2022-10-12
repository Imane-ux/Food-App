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

import com.google.firebase.auth.FirebaseAuth;

public class StartFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_start, container, false);
        Button button= (Button) view.findViewById(R.id.btnRegClient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= getParentFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, new RegClientFragment()).addToBackStack("ClientRegFragment").commit();
            }
        });
        Button button1= (Button) view.findViewById(R.id.btnRegCook);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= getParentFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                //fragmentTransaction.remove(StartFragment.this).commit();
                fragmentTransaction.add(R.id.fragmentContainer, new RegCookFragment()).addToBackStack("CookRegFragment").commit();
            }
        });
        Button button2= (Button) view.findViewById(R.id.btnLog);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager= getParentFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                //fragmentTransaction.remove(StartFragment.this).commit();
                fragmentTransaction.add(R.id.fragmentContainer, new LoginFragment()).addToBackStack("LoginFragment").commit();
            }
        });




        return view;

    }
    /*public void regOnClickListener(View view ){
        FragmentManager fragmentManager= getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.remove(this).commit();
        fragmentTransaction.add(R.id.fragmentContainer, new RegClientFragment()).addToBackStack("ClientRegFragment").commit();
    }
    public void reg2OnClickListener(View view){
        FragmentManager fragmentManager= getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new RegCookFragment()).addToBackStack("CookRegFragment").commit();

    }
    public void loginOnClickListener(View view){
        FragmentManager fragmentManager= getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new LoginFragment()).addToBackStack("LoginFragment").commit();

    }*/
}