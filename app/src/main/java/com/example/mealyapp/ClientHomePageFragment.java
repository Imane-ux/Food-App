package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ClientHomePageFragment extends Fragment {

    FirebaseAuth mAuth;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private DatabaseReference ref2;
    private FirebaseRecyclerAdapter<Meal, MyViewHolder3> madapter2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_home_page, container, false);

        Button btn1= view.findViewById(R.id.btn_Vp);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new ClientPurchasesFragment()).commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ref2 = FirebaseDatabase.getInstance().getReference("Meals To Clients");
        recyclerView2= getActivity().findViewById(R.id.recyclerPurchaseRequest);
        recyclerView2.setHasFixedSize(true);
        layoutManager2= new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Meal> options = new FirebaseRecyclerOptions.Builder<Meal>()
                .setQuery(ref2, Meal.class).build();
        // filtered here?
        madapter2 = new FirebaseRecyclerAdapter<Meal, MyViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder3 holder, int position, @NonNull Meal model) {

                final String mealCookID= getRef(position).getKey();
                //DatabaseReference re = ref2.child(mealCookID);

                holder.itemName.setText(model.getName());
                holder.itemType.setText(model.getType());
                holder.itemCuisine.setText(model.getCuisine());
                holder.itemIngredient.setText(model.getIngredients());
                holder.itemAllergens.setText(model.getAllergens());
                holder.itemPrice.setText(model.getPrice());
                holder.itemDescription.setText(model.getDescription());
                String cookUID= model.getCookId();


                /*final String cookUID;
                if (mealCookID.charAt(0)== '-'){
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference(getRef(position).getKey());
                    cookUID= ref.getKey();
                }else{
                    cookUID= getRef(position).getKey();
                }*/
                DatabaseReference re1 = FirebaseDatabase.getInstance().getReference("user").child(cookUID);
                re1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name= String.valueOf(snapshot.child("firstName").getValue());
                        String address= String.valueOf(snapshot.child("address").getValue());
                        String description= String.valueOf(snapshot.child("description").getValue());
                        holder.itemCookName.setText(name);
                        holder.itemCookAddress.setText(address);
                        holder.itemCookDescription.setText(description);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userID = mAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Purchase Requests").child(userID);
                        String id= ref.push().getKey();
                        ref.child(id).setValue(new PurchaseRequest(model.getName()));

                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Requests");
                        reff.child(cookUID).setValue(new Request( userID, model.getName()));
                    }
                });

                }


            //}

            @NonNull
            @Override
            public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offeredmeal_clientview, parent, false);
                ClientHomePageFragment.MyViewHolder3 vh = new MyViewHolder3(view);
                return vh;
            }
        };
        madapter2.startListening();
        recyclerView2.setAdapter(madapter2);

    }


    public static class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemType;
        public TextView itemCuisine;
        public TextView itemIngredient;
        public TextView itemAllergens;
        public TextView itemDescription;
        ImageButton add;
        public TextView itemCookName;
        public TextView itemCookAddress;
        public TextView itemCookDescription;
        public TextView itemPrice;

        public MyViewHolder3(View itemView) {
            super(itemView);
            itemName= itemView.findViewById(R.id.mealNamec);
            itemType= itemView.findViewById(R.id.mealTypec);
            itemCuisine= itemView.findViewById(R.id.cuisineTypec);
            itemIngredient=  itemView.findViewById(R.id.listIngredientsC);
            itemAllergens= itemView.findViewById(R.id.allergensIDc);
            itemDescription= itemView.findViewById(R.id.mealDescriptionIdc);
            add= itemView.findViewById(R.id.imgPlusButtonc);
            itemCookName= itemView.findViewById(R.id.cookNamec);
            itemCookAddress= itemView.findViewById(R.id.cookAddressc);
            itemCookDescription= itemView.findViewById(R.id.cookDescriptionIdc);
            itemPrice= itemView.findViewById(R.id.priceMealc);
        }
    }
}