package com.mobdeve.titan.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.AdminEventActivity;
import com.mobdeve.titan.ViewHolders.EventViewHolder;
import com.mobdeve.titan.Models.EventModel;
import com.mobdeve.titan.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private ArrayList<EventModel> events;
    private boolean isToday;

    public EventAdapter(ArrayList<EventModel> events, boolean isToday) {
        this.events = events;
        this.isToday = isToday;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_event, parent, false);

        EventViewHolder eventViewHolder = new EventViewHolder(view);

        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setEventName(this.events.get(position).getName(), this.isToday);
        holder.setEventSchedule(this.events.get(position).toStringOpeningHours());

        holder.getClEventItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPosition = holder.getBindingAdapterPosition();

                Intent intent = new Intent(v.getContext(), AdminEventActivity.class);
                intent.putExtra(String.valueOf(R.string.id_event_id), events.get(currPosition).getKey());
                intent.putExtra(String.valueOf(R.string.id_event_name), events.get(currPosition).getName());
                intent.putExtra(String.valueOf(R.string.id_event_schedule), events.get(currPosition).toStringOpeningHours());
                intent.putExtra(String.valueOf(R.string.id_event_address), events.get(currPosition).getAddress());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void addEvent(EventModel event) {
        this.events.add(event);
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
