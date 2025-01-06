package com.example.pregaboo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Mom;
import com.example.pregaboo.views.MomProfileActivity;

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
        holder.momIdTextView.setText("Mom ID: " + mom.getMomId());
        holder.nameTextView.setText("Name: " + mom.getName());
        holder.pregnancyDateTextView.setText("Pregnancy Date: " + String.valueOf(mom.getPregnancyDate()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MomProfileActivity.class);
            intent.putExtra("MOM_ID", mom.getMomId());
            intent.putExtra("MOM_NAME", mom.getName());
            intent.putExtra("PREGNANCY_DATE", mom.getPregnancyDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return momList.size();
    }

    public void updateData(List<Mom> newMomList) {
        momList.clear();
        momList.addAll(newMomList);
        notifyDataSetChanged();
    }

    public static class MomViewHolder extends RecyclerView.ViewHolder {
        TextView momIdTextView;
        TextView nameTextView;
        TextView pregnancyDateTextView;

        public MomViewHolder(@NonNull View itemView) {
            super(itemView);
            momIdTextView = itemView.findViewById(R.id.momIdTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            pregnancyDateTextView = itemView.findViewById(R.id.pregnancyDateTextView);
        }
    }
}