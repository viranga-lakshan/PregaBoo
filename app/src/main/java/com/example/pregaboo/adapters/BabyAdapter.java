package com.example.pregaboo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Baby;
import com.example.pregaboo.views.BabySeeEachDetailsDashboard;
import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.BabyViewHolder> {
    private List<Baby> babyList;
    private Context context;
    private String userId;

    public BabyAdapter(List<Baby> babyList, Context context, String userId) {
        this.babyList = babyList;
        this.context = context;
        this.userId = userId;
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
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BabySeeEachDetailsDashboard.class);
            intent.putExtra("BABY_ID", baby.getBabyId());
            intent.putExtra("USER_ID", userId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return babyList.size();
    }

    public static class BabyViewHolder extends RecyclerView.ViewHolder {
        TextView babyNameTextView;
        Button babyButton;

        public BabyViewHolder(@NonNull View itemView) {
            super(itemView);
            babyNameTextView = itemView.findViewById(R.id.babyNameTextView);
            babyButton = itemView.findViewById(R.id.babyButton);
        }
    }
}