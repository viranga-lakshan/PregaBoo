package com.example.pregaboo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.GrowthData;
import java.util.List;

public class GrowthDataAdapter extends RecyclerView.Adapter<GrowthDataAdapter.ViewHolder> {
    private List<GrowthData> growthDataList;

    public GrowthDataAdapter(List<GrowthData> growthDataList) {
        this.growthDataList = growthDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_growth_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GrowthData growthData = growthDataList.get(position);
        holder.dateTextView.setText(String.valueOf(growthData.getDate()));
        holder.heightTextView.setText(String.valueOf(growthData.getHeight()));
        holder.weightTextView.setText(String.valueOf(growthData.getWeight()));
    }

    @Override
    public int getItemCount() {
        return growthDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView heightTextView;
        TextView weightTextView;

        ViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.dateTextView);
            heightTextView = view.findViewById(R.id.heightTextView);
            weightTextView = view.findViewById(R.id.weightTextView);
        }
    }
} 