package com.example.pregaboo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.ClinicDate;
import java.util.List;

public class ClinicDateAdapter extends RecyclerView.Adapter<ClinicDateAdapter.ClinicDateViewHolder> {
    private List<ClinicDate> clinicDateList;
    private Context context;

    public ClinicDateAdapter(List<ClinicDate> clinicDateList, Context context) {
        this.clinicDateList = clinicDateList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClinicDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinic_date, parent, false);
        return new ClinicDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicDateViewHolder holder, int position) {
        ClinicDate clinicDate = clinicDateList.get(position);
        holder.dateTextView.setText(clinicDate.getDate());
        holder.timeTextView.setText(clinicDate.getTime());
        holder.purposeTextView.setText(clinicDate.getPurpose());
    }

    @Override
    public int getItemCount() {
        return clinicDateList.size();
    }

    public static class ClinicDateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView timeTextView;
        TextView purposeTextView;

        public ClinicDateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            purposeTextView = itemView.findViewById(R.id.purposeTextView);
        }
    }
} 