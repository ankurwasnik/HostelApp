package com.example.hostelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ComplaintAdapter extends FirestoreRecyclerAdapter < ComplaintClass , ComplaintAdapter.ComplaintHolder> {

    public ComplaintAdapter(@NonNull FirestoreRecyclerOptions<ComplaintClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ComplaintHolder holder, int position, @NonNull ComplaintClass model) {
        //put in each view card
        holder.textViewTitle.setText(model.getSubject());
        holder.textViewDetails.setText(model.getDetails());

    }

    @NonNull
    @Override
    public ComplaintHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_cards,viewGroup,false);

        return new ComplaintHolder(v);
    }
    public  void  deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    class ComplaintHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle , textViewDetails ;

        public ComplaintHolder(@NonNull View itemView) {
            super(itemView);
            textViewDetails=itemView.findViewById(R.id.tvComplaint_card_details);
            textViewTitle=itemView.findViewById(R.id.tvComplaint_card_title);
        }
    }
}
