package com.mobdeve.titan;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private TextView tvEventName, tvEventSchedule;
    private ConstraintLayout clEventItem;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvEventName = itemView.findViewById(R.id.tv_item_event_name);
        this.tvEventSchedule = itemView.findViewById(R.id.tv_item_schedule);
        this.clEventItem = itemView.findViewById(R.id.cl_event_item);
    }

    public void setEventName(String eventName, boolean today) {
        if(today) this.tvEventName.setTextColor(Color.parseColor("#83C5BE"));
        this.tvEventName.setText(eventName);
    }

    public void setEventSchedule(String schedule) {
        this.tvEventSchedule.setText(schedule);
    }

    public ConstraintLayout getClEventItem() {
        return clEventItem;
    }
}
