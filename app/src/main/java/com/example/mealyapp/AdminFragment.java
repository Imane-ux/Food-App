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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_admin, container, false);

        /*recyclerView= getActivity().findViewById(R.id.recyclerView1);
        ref= FirebaseDatabase.getInstance().getReference().child("Complaints");
        layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);*/




        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView= getActivity().findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        ref= FirebaseDatabase.getInstance().getReference().child("Complaints");
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Complaint> options = new FirebaseRecyclerOptions.Builder<Complaint>()
                .setQuery(ref, Complaint.class).build();

        FirebaseRecyclerAdapter<Complaint, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Complaint, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Complaint model) {
                holder.itemName.setText(model.getCookUID());
                holder.itemComplaint.setText(model.getComplaint());
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_view, parent, false);
                MyViewHolder vh = new MyViewHolder(view);
                return vh;
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView itemName;
            TextView itemComplaint;
            //TextView ver;
            public MyViewHolder(View itemView) {
                super(itemView);
                //ver= itemView;
                itemName= itemView.findViewById(R.id.ismCook);
                itemComplaint= itemView.findViewById(R.id.ismComplaint);
            }
        }

    }
//}