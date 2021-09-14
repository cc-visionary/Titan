package com.mobdeve.titan.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.AdminEventActivity;
import com.mobdeve.titan.Models.EventModel;
import com.mobdeve.titan.R;
import com.mobdeve.titan.UserEventActivity;
import com.mobdeve.titan.ViewHolders.EventViewHolder;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private ArrayList<EventModel> events;

    public EventListAdapter(ArrayList<EventModel> events) {
        this.events = events;
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
        holder.setEventName(this.events.get(position).getName(), false);
        holder.setEventSchedule(this.events.get(position).toStringOpeningHours());

        holder.getClEventItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPosition = holder.getBindingAdapterPosition();

                Intent intent = new Intent(v.getContext(), UserEventActivity.class);
                intent.putExtra(String.valueOf(R.string.id_event_id), events.get(currPosition).getKey());
                intent.putExtra(String.valueOf(R.string.id_event_name), events.get(currPosition).getName());
                intent.putExtra(String.valueOf(R.string.id_event_schedule), events.get(currPosition).toStringOpeningHours());
                intent.putExtra(String.valueOf(R.string.id_event_address), events.get(currPosition).getAddress());
                intent.putExtra(String.valueOf(R.string.id_event_contact), events.get(currPosition).getContactNumber());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setEvents(ArrayList<EventModel> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
