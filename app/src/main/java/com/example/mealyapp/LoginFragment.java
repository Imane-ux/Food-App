package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        TextView invalidText = (TextView) view.findViewById(R.id.invalid_text);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   loginUser(emailInput.getText().toString(), passwordInput.getText().toString(), view);
               }
               catch (Exception e){
                   invalidText.setVisibility(View.VISIBLE);
               }

           }
        });


        //put your code here for name, email, address... Use the login user method below once u get the strings.

        return view;

    }

    private void loginUser(String txt1, String txt2, View view) {
        mAuth.signInWithEmailAndPassword(txt1,txt2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // String id = mDatabase.push().getKey();

                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new Welcome()).commit();


                }else {
                    TextView invalidText = (TextView) view.findViewById(R.id.invalid_text);
                    invalidText.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}