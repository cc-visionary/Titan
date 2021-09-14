package com.mobdeve.titan.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.R;

public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
    private ConstraintLayout constraintLayout;
    private TextView startTimeTextView, endTimeTextView, userTextView;

    public TimeSlotViewHolder(@NonNull View itemView) {
        super(itemView);

        this.constraintLayout = itemView.findViewById(R.id.cl_timeslot_item);
        this.startTimeTextView = itemView.findViewById(R.id.tv_time_slot_start);
        this.endTimeTextView = itemView.findViewById(R.id.tv_time_slot_end);
        this.userTextView = itemView.findViewById(R.id.tv_time_slot_person);
    }

    public void setStartTime(String startTime) {
        this.startTimeTextView.setText(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTimeTextView.setText(endTime);
    }

    public void setUser(String email) {
        this.userTextView.setText(email);
    }

    public ConstraintLayout getConstraintLayout() {
        return constraintLayout;
    }
}
