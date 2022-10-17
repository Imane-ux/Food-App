package com.example.mealyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();

        EditText emailInput = (EditText) view.findViewById(R.id.email_input);
        EditText passwordInput = (EditText) view.findViewById(R.id.password_input);
        Button loginButton = (Button) view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
<<<<<<< HEAD
           @Override
           public void onClick(View v) {
               if(emailInput.getText().toString().equals("Admin") && passwordInput.getText().toString().equals("group28")){
                   Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                   FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                   fragmentTransaction.replace(R.id.fragmentContainer, new AdminFragment()).commit();
               }
               else {
                   loginUser(emailInput.getText().toString(), passwordInput.getText().toString());
               }
           }
=======
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().equals("Admin") && passwordInput.getText().toString().equals("group28")){
                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new AdminFragment()).commit();
                }
                else {
                    loginUser(emailInput.getText().toString(), passwordInput.getText().toString());
                }
            }
>>>>>>> 7c1d8b5ea3622482ab48a6a0987f753370e123ea
        });


        //put your code here for name, email, address... Use the login user method below once u get the strings.

        return view;

    }

    private void loginUser(String txt1, String txt2) {
        mAuth.signInWithEmailAndPassword(txt1, txt2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new ClientPageFragment()).commit();
                return;
                // code to start a new fragment here (welcome Client fragment)
                // check if u need to remove the fragment b4 passing to the next.(prolly yes)

            }
        });

        Toast.makeText(getActivity(), "Incorrect credentials!", Toast.LENGTH_SHORT).show();
    }

//    txt1.equals("Admin") && txt2.equals("christmas")

//    private void tryLogin(String txt1, String txt2){
//        if(true){
//            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
//
//            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.fragmentContainer, new ClientPageFragment()).commit();
//        }
//        else {
//            Toast.makeText(getActivity(), "Incorrect credentials!", Toast.LENGTH_SHORT).show();
//        }
//    }
}