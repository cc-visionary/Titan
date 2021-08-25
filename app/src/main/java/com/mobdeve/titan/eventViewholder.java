package com.mobdeve.titan;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class eventViewholder extends RecyclerView.ViewHolder
{
    private TextView startTime;
    private TextView endTime;
    private TextView eventName;
    private TextView slots;
    private TextView timeAlloted;


    public eventViewholder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.startTime= itemView.findViewById(R.id.starting_tv);
        this.endTime= itemView.findViewById(R.id.ending_tv);
        this.eventName= itemView.findViewById(R.id.event_name);
        this.timeAlloted= itemView.findViewById(R.id.time_tv);
        this.slots= itemView.findViewById(R.id.slots_tv);


    }


    public void setEndTime(String endTime) {
        this.endTime.setText(endTime);
    }

    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    public void setSlots(int slots) {
        this.slots.setText(String.valueOf(slots));
    }

    public void setStartTime(String startTime) {
        this.startTime.setText(startTime);
    }

    public void setTimeAlloted(int timeAlloted) {
        this.timeAlloted.setText(String.valueOf(timeAlloted));
    }
}
