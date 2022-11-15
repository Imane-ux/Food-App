package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CookHomePage extends Fragment {

    FirebaseAuth mAuth;
    Cook cook;
    ArrayList<Complaint> complaints;
    int daysOfBanLeft = 0;
    boolean permanentlyBanned = false;
    RelativeLayout mainLayout, bannedLayout;
    TextView bannedText;

    private RecyclerView recyclerView0;
    private RecyclerView recyclerView1;
    private RecyclerView.LayoutManager layoutManager0;
    private RecyclerView.LayoutManager layoutManager1;
    private DatabaseReference ref0;
    private DatabaseReference dr;
    private FirebaseRecyclerAdapter<Meal, MyViewHolder1> madapter;
    private FirebaseRecyclerAdapter<Meal, MyViewHolder2> adapter1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook_home_page, container, false);

        mAuth = FirebaseAuth.getInstance();
        complaints = new ArrayList<>();

        Button btn2= view.findViewById(R.id.logOut2_cook);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {logout(); }
        });

        Button btn= view.findViewById(R.id.logOut_cook);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        ImageButton btnAdd= view.findViewById(R.id.imageBtn3);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new AddMealFragment()).commit();

            }
        });
        mainLayout = view.findViewById(R.id.main_layout);
        bannedLayout = view.findViewById(R.id.banned_layout);
        bannedText = view.findViewById(R.id.banned_text);
        getCookDetails();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView0= getActivity().findViewById(R.id.recyclerViewcook2);
        recyclerView0.setHasFixedSize(true);
        ref0= FirebaseDatabase.getInstance().getReference("Meals").child(mAuth.getUid());
        layoutManager0= new LinearLayoutManager(getActivity());
        recyclerView0.setLayoutManager(layoutManager0);

        dr = FirebaseDatabase.getInstance().getReference("Offered Meals").child(mAuth.getUid());
        recyclerView1= getActivity().findViewById(R.id.recyclerViewcook1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1= new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);

    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView= view.findViewById(R.id.recyclerViewcook2);
        recyclerView.setHasFixedSize(true);
        //ref= FirebaseDatabase.getInstance().getReference().child("Meals").child(mAuth.getUid());
        layoutManager= new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(layoutManager);
        //adapter.notifyDataSetChanged();
        madapter =  new MealsAdapter(getContext(), meals);
        recyclerView.setLayoutManager(layoutManager);
        //adapter.startListening();
        recyclerView.setAdapter(madapter);
        //madapter.notifyDataSetChanged();
    }*/
    @Override
    public void onStart() {
        super.onStart();
        // creating firebase recycler for only the menu not the current offered meals
        FirebaseRecyclerOptions<Meal> options = new FirebaseRecyclerOptions.Builder<Meal>()
                .setQuery(ref0, Meal.class).build();
        madapter = new FirebaseRecyclerAdapter<Meal, MyViewHolder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder1 holder, int position, @NonNull Meal model) {

                final String mealID= getRef(position).getKey();
                DatabaseReference re= ref0.child(mealID);

                holder.itemName.setText(model.getName());
                holder.itemType.setText(model.getType());
                holder.itemCuisine.setText(model.getCuisine());
                holder.itemIngredient.setText(model.getIngredients());
                holder.itemAllergens.setText(model.getAllergens());
                holder.itemDescription.setText(model.getDescription());
                holder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //DatabaseReference re= ref0.child(mealID);
                        re.removeValue();
                        Toast.makeText(getActivity(), "Meal deleted", Toast.LENGTH_LONG).show();
                    }
                });
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        re.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Meal meal= snapshot.getValue(Meal.class);
                                String id = dr.push().getKey();
                                dr.child(id).setValue(meal);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_view, parent, false);
                CookHomePage.MyViewHolder1 vh1 = new MyViewHolder1(view);
                return vh1;
            }
        };
        madapter.startListening();
        recyclerView0.setAdapter(madapter);

        // creating the firebase recycler view for the currently offered meal
        FirebaseRecyclerOptions<Meal> options1 = new FirebaseRecyclerOptions.Builder<Meal>()
                .setQuery(dr, Meal.class).build();
        adapter1= new FirebaseRecyclerAdapter<Meal, MyViewHolder2>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, int position, @NonNull Meal model) {

                final String off_mealID= getRef(position).getKey();
                DatabaseReference re= dr.child(off_mealID);

                holder.itemName.setText(model.getName());
                holder.itemType.setText(model.getType());
                holder.itemCuisine.setText(model.getCuisine());
                holder.itemIngredient.setText(model.getIngredients());
                holder.itemAllergens.setText(model.getAllergens());
                holder.itemDescription.setText(model.getDescription());
                holder.itemRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        re.removeValue();
                        Toast.makeText(getActivity(), "Meal removed from the currently offered meals' list", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offeredmeal_view, parent, false);
                CookHomePage.MyViewHolder2 vh = new MyViewHolder2(view);
                return vh;
            }
        };
        adapter1.startListening();
        recyclerView1.setAdapter(adapter1);

    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
        adapter1.stopListening();
    }

    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
        FragmentManager fragmentManager= getParentFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new StartFragment()).addToBackStack(null).commit();
    }


    // Retrieving all cook's attributes from firebase;
    public void getCookDetails()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getCookData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getCookData(DataSnapshot dataSnapshot)
    {
        String role = String.valueOf(dataSnapshot.child("role").getValue());
        String password = String.valueOf(dataSnapshot.child("password").getValue());
        String email = String.valueOf(dataSnapshot.child("email").getValue());
        String firstName = String.valueOf(dataSnapshot.child("firstName").getValue());
        String lastName = String.valueOf(dataSnapshot.child("lastName").getValue());
        String address = String.valueOf(dataSnapshot.child("address").getValue());
        String description = String.valueOf(dataSnapshot.child("description").getValue());
        ArrayList<String> complaintStrings = new ArrayList<>();

        for (DataSnapshot snapshot: dataSnapshot.child("complaints").getChildren()) {
            complaintStrings.add(snapshot.getValue().toString());
        }

//        Toast.makeText(getActivity(), complaintStrings.size() + " ", Toast.LENGTH_SHORT).show();
        cook = new Cook(role, password, email, firstName, lastName, address, description, complaintStrings);

        getComplaints(cook);

    }

    // Getting all complaints associated with the current cook.
    public void getComplaints(Cook cook)
    {
        for(int i = 0; i < cook.getComplaints().size(); i++) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Complaints");
            reference.child(cook.getComplaints().get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Toast.makeText(getActivity(), cook.getComplaints().size() + " ", Toast.LENGTH_LONG).show();
                    getComplaintsData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    public void getComplaintsData(DataSnapshot dataSnapshot)
    {
        String cookUID = String.valueOf(dataSnapshot.child("cookUID").getValue());
        String complaint = String.valueOf(dataSnapshot.child("complaint").getValue());
//        String startOfBan = String.valueOf(dataSnapshot.child("startOfBan").getValue());
//        String days = String.valueOf(dataSnapshot.child("daysOfTemporaryBan").getValue());
//        String permanent = String.valueOf(dataSnapshot.child("permanentBan").getValue());
        int daysOfTemporaryBan = 0;
        boolean permanentBan = false;

        if(String.valueOf(dataSnapshot.child("daysOfTemporaryBan").getValue()).equals("15"))
        {
            daysOfTemporaryBan = 15;
            daysOfBanLeft = 15;
        }
        if(String.valueOf(dataSnapshot.child("permanentBan").getValue()).equals("true"))
        {
            permanentBan = true;
            permanentlyBanned = true;
        }

        complaints.add(new Complaint(cookUID, complaint, null, daysOfTemporaryBan, permanentBan));

        checkBan(complaints);
    }

    // Checking if the cook is banned.
    public void checkBan(ArrayList<Complaint> complaints)
    {
        if(!complaints.get(0).getPermanentBan())
        {
            bannedText.append("suspended for 15 days!");
            mainLayout.setVisibility(View.INVISIBLE);
            bannedLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            bannedText.append("banned permanently!");
            mainLayout.setVisibility(View.INVISIBLE);
            bannedLayout.setVisibility(View.VISIBLE);
        }
    }

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemType;
        public TextView itemCuisine;
        public TextView itemIngredient;
        public TextView itemAllergens;
        public TextView itemDescription;
        ImageButton del;
        ImageButton add;



        public MyViewHolder1(View itemView) {
            super(itemView);
            itemName= itemView.findViewById(R.id.mealName0);
            itemType= itemView.findViewById(R.id.mealType);
            itemCuisine= itemView.findViewById(R.id.cuisineType);
            itemIngredient=  itemView.findViewById(R.id.listIngredients);
            itemAllergens= itemView.findViewById(R.id.allergensID);
            itemDescription= itemView.findViewById(R.id.mealDescriptionId);
            del= itemView.findViewById(R.id.imgDelButton);
            add= itemView.findViewById(R.id.imgPlusButton);
        }
    }
    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemType;
        public TextView itemCuisine;
        public TextView itemIngredient;
        public TextView itemAllergens;
        public TextView itemDescription;
        ImageButton itemRemove;


        public MyViewHolder2(View itemView) {
            super(itemView);
            itemName= itemView.findViewById(R.id.mealName1);
            itemType= itemView.findViewById(R.id.mealType1);
            itemCuisine= itemView.findViewById(R.id.cuisineType1);
            itemIngredient=  itemView.findViewById(R.id.listIngredients1);
            itemAllergens= itemView.findViewById(R.id.allergensID1);
            itemDescription= itemView.findViewById(R.id.mealDescriptionId1);
            itemRemove= itemView.findViewById(R.id.imgREMButton1);
        }
    }
}