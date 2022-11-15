package com.example.mealyapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegClientFragment extends Fragment {

    FirebaseAuth mAuth;
    Button inRegister, backToLogin;
    EditText inPassword;
    TextInputEditText inEmail,inFirstName,inLastName,inAddress,inCardNumber,inExpiryYear,inExpiryMonth,inSecurityCode,inNameOnCard;
    String password,email,firstName,lastName,address,cardNumber,expiryYear,expiryMonth,securityCode,nameOnCard;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private static final String USER = "user";
    private static final String TAG = "RegClientFragment";
    User user;

   @SuppressLint("MissingInflatedId")
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view =inflater.inflate(R.layout.fragment_reg_client, container, false);
       inPassword = view.findViewById(R.id.inPassword);
       inEmail = view.findViewById(R.id.inEmail);
       inFirstName = view.findViewById(R.id.inFirstName);
       inLastName = view.findViewById(R.id.inLastName);
       inAddress = view.findViewById(R.id.inAddress);
       inCardNumber = view.findViewById(R.id.inCardNumber);
       inExpiryYear = view.findViewById(R.id.inExpiryYear);
       inExpiryMonth = view.findViewById(R.id.inExpiryMonth);
       inSecurityCode = view.findViewById(R.id.inSecurityCode);
       inNameOnCard = view.findViewById(R.id.inNameOnCard);
       inRegister = view.findViewById(R.id.inRegister);

       mAuth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();
       mDatabase = FirebaseDatabase.getInstance().getReference(USER);

       inRegister= (Button) view.findViewById(R.id.inRegister);
       backToLogin= (Button) view.findViewById(R.id.backToLogin);
       backToLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.fragmentContainer, new StartFragment()).commit();

           }
       });


       inRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              password = inPassword.getText().toString();
              email = inEmail.getText().toString();
              firstName = inFirstName.getText().toString();
              lastName = inLastName.getText().toString();
              address = inAddress.getText().toString();
               cardNumber = inCardNumber.getText().toString();
               expiryYear = inExpiryYear.getText().toString();
               expiryMonth = inExpiryMonth.getText().toString();
               securityCode = inSecurityCode.getText().toString();
               nameOnCard = inNameOnCard.getText().toString();

               if (TextUtils.isEmpty(firstName)){
                   inFirstName.setError("Enter first name!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(lastName)){
                   inLastName.setError("Enter last name!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(address)){
                   inAddress.setError("Enter address!");
                   inRegister.setClickable(false);


               }
               if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   inEmail.setError("Enter valid email!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(cardNumber)){
                   inCardNumber.setError("Enter card number!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(password)||password.length()<5){
                   inPassword.setError("Password can not be less than 5 characters!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(expiryMonth)){
                   inExpiryMonth.setError("Enter expiry month!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(expiryYear)){
                   inExpiryYear.setError("Enter expiry year!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(securityCode)){
                   inSecurityCode.setError("Enter security code!");
                   inRegister.setClickable(false);


               }
               if (TextUtils.isEmpty(nameOnCard)){
                   inNameOnCard.setError("Enter card name!");
                   inRegister.setClickable(false);


               }else{

                   inRegister.setClickable(true);
                   registerUser(email, password);
               }

              // mAuth = FirebaseAuth.getInstance();



           }


       });
       return view;
        //mAuth = FirebaseAuth.getInstance();

       //put your code here. "onclick listener for the reg btn/ get the inputs as texts, check if they are empty and make
        //a toast msg to the user. set a password length to surpass 5 characters for exp... if all good register him using
        // the method below.




    }

    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                   // String id = mDatabase.push().getKey();

                    String userID = mAuth.getInstance().getCurrentUser().getUid();
                    user  = new Client("Client",password,email,firstName,lastName,address,cardNumber,expiryYear,expiryMonth,securityCode, nameOnCard);
                    mDatabase.child(userID).setValue(user);

                    //if(  mAuth.getInstance().getCurrentUser() != null){
                      //  String userID = mAuth.getInstance().getCurrentUser().getUid();
                    //}

                    Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new WelcomeClientFragment()).commit();


                }else {
                    Toast.makeText(getActivity(), "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}