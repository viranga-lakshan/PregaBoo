package com.example.pregaboo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Mom;

import java.util.List;

public class MomAdapter extends RecyclerView.Adapter<MomAdapter.MomViewHolder> {
    private List<Mom> momList;
    private Context context;

    public MomAdapter(List<Mom> momList, Context context) {
        this.momList = momList;
        this.context = context;
    }

    @NonNull
    @Override
    public MomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mom, parent, false);
        return new MomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomViewHolder holder, int position) {
        Mom mom = momList.get(position);
        holder.momIdTextView.setText(mom.getMomId()); // Set the mom ID
        holder.nameTextView.setText(mom.getName());
        holder.pregnancyDateTextView.setText(String.valueOf(mom.getPregnancyDate()));
        holder.locationTextView.setText(mom.getLocation());
    }

    @Override
    public int getItemCount() {
        return momList.size();
    }

    public static class MomViewHolder extends RecyclerView.ViewHolder {
        TextView momIdTextView;
        TextView nameTextView;
        TextView pregnancyDateTextView;
        TextView locationTextView;

        public MomViewHolder(@NonNull View itemView) {
            super(itemView);
            momIdTextView = itemView.findViewById(R.id.momIdTextView); // Ensure this ID matches your layout
            nameTextView = itemView.findViewById(R.id.nameTextView);
            pregnancyDateTextView = itemView.findViewById(R.id.pregnancyDateTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}