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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
        //recyclerView.setHasFixedSize(true);
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

                final String complainerID = getRef(position).getKey();
                String des= model.getComplaint();
                String cookUId= model.getCookUID();
                DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("user").child(cookUId);
                re.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<String> listComplaints= new ArrayList<>();
                        listComplaints.add(complainerID);
                        Map<String, Object> listcomplaints = new HashMap<>();
                        listcomplaints.put("complaints", listComplaints);
                        re.updateChildren(listcomplaints);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                holder.itemName.setText(cookUId);
                holder.itemComplaint.setText(model.getComplaint());

                holder.itemDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Dismissed", Toast.LENGTH_LONG).show();
                        deleteComplaint(complainerID);
                    }
                    });
                holder.itemBan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        suspendCookParmanently(complainerID);
                    }
                    });
                holder.itemSuspend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        suspendCookTemporarily(complainerID);
                    }
                    });
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
            Button itemBan;
            Button itemSuspend;
            Button itemDismiss;


            public MyViewHolder(View itemView) {
                super(itemView);
                itemName= itemView.findViewById(R.id.ismCook);
                itemComplaint= itemView.findViewById(R.id.ismComplaint);
                itemBan =  itemView.findViewById(R.id.btn_susp_perm);
                itemSuspend= itemView.findViewById(R.id.btn_susp_temp);
                itemDismiss= itemView.findViewById(R.id.btn_dismiss);
            }
        }



        public boolean deleteComplaint(String id){
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
            dR.removeValue();
            Toast.makeText(getActivity(), "Complaint Actioned", Toast.LENGTH_LONG).show();
            return true;
        }

        public boolean suspendCookTemporarily(String id){
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);

            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            String timestamp = ts.toString();

            Map<String, Object> temporaryBanUpdate = new HashMap<>();
            temporaryBanUpdate.put("daysOfTemporaryBan", 15);
            temporaryBanUpdate.put("startOfBan", ts);

            dR.updateChildren(temporaryBanUpdate);
            Toast.makeText(getActivity(), "Temporary Suspension Actioned", Toast.LENGTH_LONG).show();
            return true;
        }

        public boolean suspendCookParmanently(String id){
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);

            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());

            Map<String, Object> temporaryBanUpdate = new HashMap<>();
            temporaryBanUpdate.put("permanentBan", true);
            temporaryBanUpdate.put("startOfBan", ts);

            dR.updateChildren(temporaryBanUpdate);
            Toast.makeText(getActivity(), "Permanent Suspension Actioned", Toast.LENGTH_LONG).show();
            return true;
        }


    }
//}