package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RatingFragment extends Fragment {

    DataSnapshot cookSnapshot;
    Meal meal;

    Button submitButton;
    EditText reviewInput;
    RatingBar cookRatingBar;

    String cookID, cookRating, ratingCount;
    double newRating;
    double newRatingCount;

    public RatingFragment(Meal meal, DataSnapshot cookSnapshot)
    {
        this.cookID = cookSnapshot.getKey();
        this.cookSnapshot = cookSnapshot;
        this.cookRating = String.valueOf(cookSnapshot.child("rating").getValue());
        this.ratingCount = String.valueOf(cookSnapshot.child("ratingCount").getValue());
        this.newRating = 0.0;
        this.newRatingCount = 0.0;
        this.meal = meal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.remove(new CookProfileFragment()).commit();

        submitButton = view.findViewById(R.id.submit_button);
        reviewInput = view.findViewById(R.id.review_input);
        cookRatingBar = view.findViewById(R.id.cook_rating_bar);

        try{
            newRating = Double.parseDouble(cookRating);
            newRatingCount = Double.parseDouble(ratingCount);
        } catch(Exception e) {}



//        if(newRating == 0.0)
//        {
//            newRating = cookRatingBar.getRating();
//            newRatingCount = 1.0;
//        }
//        else
//        {
//            newRatingCount++;
//            newRating = ((newRating * newRatingCount) + cookRatingBar.getRating())/newRatingCount;
//        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isDone = false;

                if(cookRatingBar.getRating() != 0.0)
                {
                    newRatingCount++;
                    newRating = ((newRating * (newRatingCount-1)) + cookRatingBar.getRating())/newRatingCount;
                    newRating = Math.round(newRating * 100.0)/100.0;

                    Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                    submitRating(cookID, (newRating + ""),(newRatingCount + ""));
                    isDone = true;
                }
                if(!reviewInput.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                    submitComplaint(cookID, reviewInput.getText().toString());
                    isDone = true;
                }

                if(isDone)
                {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new ClientHomePageFragment()).commit();
                }
            }
        });

        return view;
    }

    public void submitRating(String id, String rating, String ratingCount)
    {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("user").child(id);

        Map<String, Object> map = new HashMap<>();
        map.put("rating", rating);
        map.put("ratingCount", ratingCount);

        dr.updateChildren(map);
    }

    public void submitComplaint(String id, String review)
    {
        Complaint complaint = new Complaint(id, review);

        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Complaints");
        String complaintID = dr.push().getKey();
        dr.child(complaintID).setValue(complaint);
    }
}