package com.example.pregaboo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Baby;
import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.BabyViewHolder> {
    private List<Baby> babyList;
    private Context context;

    public BabyAdapter(List<Baby> babyList, Context context) {
        this.babyList = babyList;
        this.context = context;
    }

    @NonNull
    @Override
    public BabyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baby, parent, false);
        return new BabyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BabyViewHolder holder, int position) {
        Baby baby = babyList.get(position);
        holder.babyNameTextView.setText(baby.getName());
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

    public static class BabyViewHolder extends RecyclerView.ViewHolder {
        TextView babyNameTextView;

        public BabyViewHolder(@NonNull View itemView) {
            super(itemView);
            babyNameTextView = itemView.findViewById(R.id.babyNameTextView);
        }
    }
}