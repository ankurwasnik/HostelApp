package com.example.hostelapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
public class UpdateAdapter extends FirestoreRecyclerAdapter < UpdateClass , UpdateAdapter.UpdateHolder>  {

    public UpdateAdapter(@NonNull FirestoreRecyclerOptions<UpdateClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UpdateAdapter.UpdateHolder holder, int position, @NonNull UpdateClass model) {
        //put in each view card
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDetails.setText(model.getMessage());
    }
    @NonNull
    @Override
    public UpdateAdapter.UpdateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.update_cards,viewGroup,false);
        return new UpdateAdapter.UpdateHolder(v);
    }
    class UpdateHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle , textViewDetails ;

        public UpdateHolder(@NonNull View itemView) {
            super(itemView);
            textViewDetails=itemView.findViewById(R.id.tvUpdate_card_details);
            textViewTitle=itemView.findViewById(R.id.tvUpdate_card_title);
        }
    }
}
