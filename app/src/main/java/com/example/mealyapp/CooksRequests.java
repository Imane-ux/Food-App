package com.example.mealyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CooksRequests extends Fragment {

    Button backToLogin, reject, accept, btn_back;
    FirebaseAuth mAuth;
    private PurchaseRequest pR;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private DatabaseReference ref2,ref3,ref4;
    private FirebaseRecyclerAdapter<Request, CooksRequests.MyViewHolder3> madapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cooks_requests, container, false);

        Button pending = view.findViewById(R.id.btn_pending);
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new PendingOrder()).commit();
            }
        });
        Button back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new CookHomePage()).commit();
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ref2 = FirebaseDatabase.getInstance().getReference("Requests");
        //ref3 = FirebaseDatabase.getInstance().getReference("user");
        recyclerView2= getActivity().findViewById(R.id.recyclerCookPurchaseRequest);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager( new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
            FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>()
                    .setQuery(ref2, Request.class).build();


            //ref3 = mAuth.getCurrentUser().getUid();
            madapter2 = new FirebaseRecyclerAdapter<Request, CooksRequests.MyViewHolder3>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder3 holder, int position, @NonNull Request model) {
                    holder.itemMealName.setText(model.getMealName());
                    holder.itemClientName.setText(model.getClientUID());
                    String clientID = model.getClientUID();
                    final String requestID = getRef(position).getKey();


                    holder.itemReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Request Rejected", Toast.LENGTH_LONG).show();
                            rejectRequest(requestID);
                        }
                    });

                    holder.itemAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userID = mAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests").child(userID);
                            String id= ref.push().getKey();
                            ref.child(id).setValue(new Request(model.getMealName()));

                            DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Pending Orders");
                            reff.child(requestID).setValue(new Request( clientID, model.getMealName()));
                            rejectRequest(requestID);
                           /* String id= ref.push().getKey();
                            ref.child(id).setValue(new Request(model.getMealName()));
                            //pR = new PurchaseRequest();
                           // pR.setStatus("accepted");
                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            FirebaseDatabase.getInstance().getReference("Pending Orders").setValue(dataSnapshot.getValue());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });*/

                           // DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Requests");
                            //reff.child(clientID).setValue(new Request( userID, model.getMealName()));
                            Toast.makeText(getActivity(), "Request Accepted", Toast.LENGTH_LONG).show();

                        }

                    });

                }

             /* btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, new CookHomePage()).commit();

                    }
                });*/


                @NonNull
                @Override
                public CooksRequests.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_requests, parent, false);
                    CooksRequests.MyViewHolder3 vh = new CooksRequests.MyViewHolder3(view);
                    return vh;
                }
            };
            madapter2.startListening();
            recyclerView2.setAdapter(madapter2);


    }
    public boolean rejectRequest(String id){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Requests").child(id);
        dR.removeValue();
       // Toast.makeText(getActivity(), "Request Actioned", Toast.LENGTH_LONG).show();
        return true;
    }


    public static class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView itemMeal;
        public TextView itemClient;
        public TextView itemClientName;
        public TextView itemMealName;
        public Button itemReject;
        public Button itemAccept;



        public MyViewHolder3(View itemView) {
            super(itemView);
            itemReject = itemView.findViewById(R.id.reject);
            itemAccept = itemView.findViewById(R.id.accept);
            itemMeal= itemView.findViewById(R.id.mealN0);
            itemClient= itemView.findViewById(R.id.clientN);
            itemClientName= itemView.findViewById(R.id.clientName);
            itemMealName = itemView.findViewById(R.id.mealNO1);


        }
    }
}


