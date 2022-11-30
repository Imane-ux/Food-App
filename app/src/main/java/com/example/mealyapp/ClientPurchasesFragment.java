package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ClientPurchasesFragment extends Fragment {
    FirebaseAuth mAuth;
    private RecyclerView recyclerV;
    private RecyclerView.LayoutManager layoutM;
    private DatabaseReference refp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_client_purchases, container, false);
        return v;

    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refp = FirebaseDatabase.getInstance().getReference("Purchase Requests");
        recyclerV= getActivity().findViewById(R.id.recyclerPurchaseRequest);
        recyclerV.setHasFixedSize(true);
        layoutM= new LinearLayoutManager(getActivity());
        recyclerV.setLayoutManager(layoutM);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<PurchaseRequest> options = new FirebaseRecyclerOptions.Builder<PurchaseRequest>()
                .setQuery(refp.child(mAuth.getUid()), PurchaseRequest.class).build();

        FirebaseRecyclerAdapter<PurchaseRequest, MyViewHolder4> madapterP = new FirebaseRecyclerAdapter<PurchaseRequest, MyViewHolder4>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder4 holder, int position, @NonNull PurchaseRequest model) {

                final String ClientID= getRef(position).getKey();
                holder.itemMealName.setText(model.getMeal());
                holder.itemMealStatus.setText(model.getStatus());
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Not yet implemented, but should allow you to make a complaint and add a rating", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchaserequest_view, parent, false);
                MyViewHolder4 vh = new MyViewHolder4(view);
                return vh;
            }
        };
        madapterP.startListening();
        recyclerV.setAdapter(madapterP);
    }


    public static class MyViewHolder4 extends RecyclerView.ViewHolder {
        public TextView itemMealName;
        public TextView itemMealStatus;
        ImageButton add;


        public MyViewHolder4(View itemView) {
            super(itemView);
            itemMealName= itemView.findViewById(R.id.mealN0);
            itemMealStatus= itemView.findViewById(R.id.mealStatusC);
            add= itemView.findViewById(R.id.imgPlusButtonP);
        }
    }
}