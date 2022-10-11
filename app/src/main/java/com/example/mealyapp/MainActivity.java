package com.example.mealyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void regOnClickListener(View view ){
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, RegClientFragment.class,null).addToBackStack("Dk").commit();
    }
    public void reg2OnClickListener(View view){
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, RegCookFragment.class,null).addToBackStack("Dka").commit();

    }
    public void loginOnClickListener(View view){
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, LoginFragment.class,null).addToBackStack("Dkwa").commit();

    }
}