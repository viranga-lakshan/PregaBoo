package com.example.pregaboo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Vaccine;
import java.util.List;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder> {
    private List<Vaccine> vaccineList;
    private Context context;

    public VaccineAdapter(List<Vaccine> vaccineList, Context context) {
        this.vaccineList = vaccineList;
        this.context = context;
    }

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaccine, parent, false);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder holder, int position) {
        Vaccine vaccine = vaccineList.get(position);
        holder.dateTextView.setText(vaccine.getDate());
        holder.vaccineNameTextView.setText(vaccine.getVaccineName());
        holder.dosageTextView.setText(vaccine.getDosage());
    }

    @Override
    public int getItemCount() {
        return vaccineList.size();
    }

    public static class VaccineViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView vaccineNameTextView;
        TextView dosageTextView;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            vaccineNameTextView = itemView.findViewById(R.id.vaccineNameTextView);
            dosageTextView = itemView.findViewById(R.id.dosageTextView);
        }
    }
} 