package com.example.mealyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class PendingOrder extends Fragment {
    Button complete_btn;
    FirebaseAuth mAuth;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private DatabaseReference ref2;
    private FirebaseRecyclerAdapter<Request, PendingOrder.MyViewHolder4> madapter2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_cook_pending_page, container, false);

        Button btn_back = v.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FirebaseAuth.getInstance().signOut();
                //Toast.makeText(getActivity(), "logged out", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new CooksRequests()).commit();
            }
        });
        return v;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ref2 = FirebaseDatabase.getInstance().getReference("Pending Orders");
        //ref3 = FirebaseDatabase.getInstance().getReference("user");
        recyclerView2= getActivity().findViewById(R.id.recyclerPendingOrders);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager( new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(ref2, Request.class).build();
        // filtered here?
        madapter2 = new FirebaseRecyclerAdapter<Request, PendingOrder.MyViewHolder4>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PendingOrder.MyViewHolder4 holder, int position, @NonNull Request model) {
                holder.itemMealName.setText(model.getMealName());
                holder.itemClientName.setText(model.getClientUID());
                final String clientID = getRef(position).getKey();
                holder.itemComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Order Completed", Toast.LENGTH_LONG).show();
                        orderComplete(clientID);
                    }
                });




            }


            @NonNull
            @Override
            public PendingOrder.MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingrequest_view, parent, false);
                PendingOrder.MyViewHolder4 vh = new PendingOrder.MyViewHolder4(view);
                return vh;
            }
        };
        madapter2.startListening();
        recyclerView2.setAdapter(madapter2);

    }
    public boolean orderComplete(String id){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Pending Orders").child(id);
        dR.removeValue();
        // Toast.makeText(getActivity(), "Request Actioned", Toast.LENGTH_LONG).show();
        return true;
    }
    public static class MyViewHolder4 extends RecyclerView.ViewHolder {

        public TextView itemClientName;
        public TextView itemMealName;
        public Button itemComplete;


        public MyViewHolder4(View itemView) {
            super(itemView);

            itemClientName= itemView.findViewById(R.id.clientName);
            itemMealName = itemView.findViewById(R.id.mealNO1);
            itemComplete = itemView.findViewById(R.id.complete_btn);


        }
    }


}