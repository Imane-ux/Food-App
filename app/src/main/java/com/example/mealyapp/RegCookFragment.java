package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class RegCookFragment extends Fragment {

    private FirebaseAuth mAuth;

    Button back, next;
    EditText inPasswordCook;
    TextInputEditText inFirstNameCook, inLastNameCook, inEmailCook, inPickupAddress, inPostalCode, inDescription;
    String passwordCook, firstNameCook, lastNameCook, emailCook, pickupAddress, postalCode, description;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_reg_cook, container, false);
        mAuth = FirebaseAuth.getInstance();

        back= (Button) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new StartFragment()).commit();
            }
        });

        inPasswordCook = view.findViewById(R.id.inPasswordCook);
        inEmailCook = view.findViewById(R.id.inEmailCook);
        inFirstNameCook = view.findViewById(R.id.inFirstNameCook);
        inLastNameCook = view.findViewById(R.id.inLastNameCook);
        inPickupAddress = view.findViewById(R.id.inPickupAddress);
        inPostalCode = view.findViewById(R.id.inPostalCode);
        inDescription = view.findViewById(R.id.inDescription);

        next= (Button) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwordCook = inPasswordCook.getText().toString();
                emailCook = inEmailCook.getText().toString();
                firstNameCook = inFirstNameCook.getText().toString();
                lastNameCook = inLastNameCook.getText().toString();
                pickupAddress = inPickupAddress.getText().toString();
                postalCode = inPostalCode.getText().toString();
                description = inDescription.getText().toString();

                if (TextUtils.isEmpty(firstNameCook)){
                    inFirstNameCook.setError("Enter first name!");
                    return;
                }
                if (TextUtils.isEmpty(lastNameCook)) {
                    inLastNameCook.setError("Enter last name!");
                    return;
                } if (TextUtils.isEmpty(pickupAddress)){
                    inPickupAddress.setError("Enter address!");
                    return;
                }  if (!Patterns.EMAIL_ADDRESS.matcher(emailCook).matches()){
                    inEmailCook.setError("Enter valid email!");
                    return;
                } if (TextUtils.isEmpty(postalCode)){
                    inPostalCode.setError("Enter card number!");
                    return;
                } if (TextUtils.isEmpty(passwordCook)||passwordCook.length()<5) {
                    inPasswordCook.setError("Password can not be less than 5 characters!");
                    return;
                } else {
                    Bundle result = new Bundle();
                    result.putString("df1", emailCook);
                    result.putString("df2", passwordCook);
                    result.putString("df3", firstNameCook);
                    result.putString("df4", lastNameCook);
                    result.putString("df5", pickupAddress);
                    result.putString("df6", postalCode);
                    result.putString("df7", description);
                    getParentFragmentManager().setFragmentResult("datafrom1", result);

                    next.setClickable(true);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new RegCookChequeFragment()).commit();
                }
            }
        });


        return view;

    }



}