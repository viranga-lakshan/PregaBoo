package com.example.pregaboo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Weight;
import java.util.List;

public class WeightJournalAdapter extends RecyclerView.Adapter<WeightJournalAdapter.ViewHolder> {
    private List<Weight> weights;

    public WeightJournalAdapter(List<Weight> weights) {
        this.weights = weights;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weight_journal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weight weight = weights.get(position);
        holder.weekText.setText("Week " + weight.getWeek());
        holder.weightText.setText(weight.getWeight() + " kg");
    }

    @Override
    public int getItemCount() {
        return weights.size();
    }

    public void updateWeights(List<Weight> newWeights) {
        this.weights = newWeights;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView weekText;
        TextView weightText;

        ViewHolder(View view) {
            super(view);
            weekText = view.findViewById(R.id.weekText);
            weightText = view.findViewById(R.id.weightText);
        }
    }
} 