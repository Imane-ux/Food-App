package com.example.mealyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddMealFragment extends Fragment {
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_meal, container, false);

        mAuth = FirebaseAuth.getInstance();
        EditText inName= view.findViewById(R.id.etName);
        EditText inType=view.findViewById(R.id.etType);
        EditText inCuisine= view.findViewById(R.id.etCuisine);
        EditText inIngredients= view.findViewById(R.id.etIngredients);
        EditText inAllergens= view.findViewById(R.id.etALlergens);
        EditText inDescription= view.findViewById(R.id.etDescription);
        EditText inPrice= view.findViewById(R.id.eTPrice);

        Button btnDone= view.findViewById(R.id.doneBTn);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName= inName.getText().toString();
                String mtype= inType.getText().toString();
                String mcuisine= inCuisine.getText().toString();
                String mIngredients= inIngredients.getText().toString();
                String mAllergens= inAllergens.getText().toString();
                String mDescription = inDescription.getText().toString();
                String mPrice= inPrice.getText().toString();
                String userID = mAuth.getInstance().getCurrentUser().getUid();


                if (TextUtils.isEmpty(mName)){
                    inName.setError("Enter your meal's name!");
                    btnDone.setClickable(false);
                }
                if (TextUtils.isEmpty(mtype)){
                    inType.setError("Enter your meal's type!");
                    btnDone.setClickable(false);
                }
                if (TextUtils.isEmpty(mcuisine)){
                    inCuisine.setError("Enter your meal's cuisine type!");
                    btnDone.setClickable(false);
                }

                if (TextUtils.isEmpty(mIngredients)){
                    inIngredients.setError("Enter your meal's ingredients!");
                    btnDone.setClickable(false);
                }
                if (TextUtils.isEmpty(mAllergens)){
                    inAllergens.setError("Enter allergens present in your meal!. If none, type none");
                    btnDone.setClickable(false);
                }
                if (TextUtils.isEmpty(mPrice)){
                    inAllergens.setError("Enter your meal's price!. It can't be for free, no? ");
                    btnDone.setClickable(false);
                }
                if (TextUtils.isEmpty(mDescription)){
                    inDescription.setError("Enter address!");
                    btnDone.setClickable(false);
                }else{

                reference= FirebaseDatabase.getInstance().getReference("Meals").child(userID);
                Meal meal= new Meal(mName, mtype,mcuisine,mIngredients,mAllergens,mPrice, mDescription, userID);

                //String userID = mAuth.getInstance().getCurrentUser().getUid();
                String id = reference.push().getKey();
                reference.child(id).setValue(meal);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new CookHomePage()).commit();
                }
            }
        });

        return view;

    }
}