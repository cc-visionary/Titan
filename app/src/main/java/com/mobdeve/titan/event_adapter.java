package com.mobdeve.titan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class event_adapter extends RecyclerView.Adapter<eventViewholder>
{
    private ArrayList<event> events;

    public event_adapter(ArrayList<event> events)
    {

        this.events=events;

    }

    @NonNull
    @NotNull
    @Override
    public eventViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_event,parent,false);
        eventViewholder eventView = new eventViewholder(itemView);




        return eventView;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull eventViewholder holder, int position) {
        holder.setEventName(events.get(position).getEventName());
        holder.setStartTime(events.get(position).getStartTime());
        holder.setEndTime(events.get(position).getEndTime());
        holder.setSlots(events.get(position).getSlots());
        holder.setTimeAlloted(events.get(position).gettimeAlloted());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
