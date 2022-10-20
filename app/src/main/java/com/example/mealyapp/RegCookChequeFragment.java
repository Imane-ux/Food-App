package com.example.mealyapp;

import static android.app.Activity.RESULT_OK;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegCookChequeFragment extends Fragment {

    private FirebaseAuth mAuth;

    protected String passwordCook, firstNameCook, lastNameCook, emailCook, pickupAddress, postalCode, description;
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private static final String USER = "user";
//    private static final String TAG = "RegCookFragment";
    Button back, registerCook;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg_cook_register_cheque, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(USER);

        getParentFragmentManager().setFragmentResultListener("datafrom1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                //  passwordCook, firstNameCook, lastNameCook, emailCook, pickupAddress, postalCode, description;

                emailCook = result.getString("df1");
                passwordCook = result.getString("df2");
                firstNameCook = result.getString("df3");
                lastNameCook = result.getString("df4");
                pickupAddress = result.getString("df5");
                postalCode = result.getString("df6");
                description = result.getString("df7");
            }
        });

        ImageView imageView = view.findViewById(R.id.imageView);
        Button takePhoto = view.findViewById(R.id.uploadCheque);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bitmap);
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    activityResultLauncher.launch(intent);
//                } else {
//                    Toast.makeText(getActivity(), "Cannot do this action", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        registerCook= (Button) view.findViewById(R.id.registerCook);
        registerCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(emailCook, passwordCook);
            }
        });


        //put your code here. for name, address... again check if the credentials are empty if they are display toasts for it.
        // check password length... if all is good register him using the method below(u'd need to complete it).

        back= (Button) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new RegCookFragment()).commit();

            }
        });

        return view;
    }

    public void registerUser(String emailCook, String passwordCook){
        mAuth.createUserWithEmailAndPassword(emailCook,passwordCook).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    String id = mDatabase.push().getKey();
                    User user;
                    user  = new User(emailCook,passwordCook,firstNameCook,lastNameCook,pickupAddress,postalCode,description);
                    mDatabase.child(id).setValue(user);

                    Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new ClientPageFragment()).commit();

                }else {
                    Toast.makeText(getActivity(), "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}