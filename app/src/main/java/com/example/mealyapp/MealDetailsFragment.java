package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class MealDetailsFragment extends Fragment {

    FirebaseAuth mAuth;
    DataSnapshot cookSnapshot;
    Meal meal;
    TextView mealTypeText, cuisineTypeText, ingredientsText, allergensText, descriptionMealText, firstNameText, lastNameText, addressText, descriptionText, ratingText;
    String mealType, cuisineType, ingredients, allergens, descriptionMeal, firstName, lastName, email, address, description, rating;

    Button reviewButton;

    public MealDetailsFragment(Meal meal, DataSnapshot cookSnapshot){
        mealType = meal.getType();
        cuisineType = meal.getCuisine();
        ingredients = meal.getIngredients();
        allergens = meal.getAllergens();
        descriptionMeal = meal.getDescription();
        firstName = String.valueOf(cookSnapshot.child("firstName").getValue());
        lastName = String.valueOf(cookSnapshot.child("lastName").getValue());
        address = String.valueOf(cookSnapshot.child("address").getValue());
        description = String.valueOf(cookSnapshot.child("description").getValue());
        rating = String.valueOf(cookSnapshot.child("rating").getValue());

        this.cookSnapshot = cookSnapshot;
        this.meal = meal;
    }

//    String mealType, String cuisineType, String ingredients, String allergens, String descriptionMeal, String firstName, String lastName, String email, String address, String description

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        mealTypeText = view.findViewById(R.id.meal_type_text);
        cuisineTypeText = view.findViewById(R.id.cuisine_type_text);
        ingredientsText = view.findViewById(R.id.ingredients_text);
        allergensText = view.findViewById(R.id.allergens_text);
        descriptionMealText = view.findViewById(R.id.description_meal_text);
        firstNameText = view.findViewById(R.id.first_name_text);
        lastNameText = view.findViewById(R.id.last_name_text);
        addressText = view.findViewById(R.id.address_text);
        descriptionText = view.findViewById(R.id.description_text);
        ratingText = view.findViewById(R.id.rating_text);

        reviewButton = view.findViewById(R.id.review_button);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, new RatingFragment(meal, cookSnapshot)).addToBackStack("RatingFragment").commit();
            }
        });

        setDetails();

        return view;
    }

    public void setDetails()
    {
        mealTypeText.setText(mealType);
        cuisineTypeText.setText(cuisineType);
        ingredientsText.setText(ingredients);
        allergensText.setText(allergens);
        descriptionMealText.setText(descriptionMeal);
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        addressText.setText(address);
        descriptionText.setText(description);

        try {
            if (Double.parseDouble(rating) > 0.0) {
                ratingText.setText(rating);
            }
        } catch(Exception e) {}
    }

}