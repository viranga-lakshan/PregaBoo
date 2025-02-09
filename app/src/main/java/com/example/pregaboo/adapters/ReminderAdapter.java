package com.example.pregaboo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Reminder;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {
    private List<Reminder> reminderList;
    private Context context;

    public ReminderAdapter(List<Reminder> reminderList, Context context) {
        this.reminderList = reminderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.noteTextView.setText(reminder.getNote());
        holder.dateTextView.setText(reminder.getDate());
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView noteTextView;
        TextView dateTextView;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}