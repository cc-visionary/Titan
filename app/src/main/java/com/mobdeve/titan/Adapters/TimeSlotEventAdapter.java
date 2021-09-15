package com.mobdeve.titan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.R;
import com.mobdeve.titan.ViewHolders.TimeSlotViewHolder;

import java.util.ArrayList;

public class TimeSlotEventAdapter extends RecyclerView.Adapter<TimeSlotViewHolder> {
    private ArrayList<AppointmentModel> appointments;

    public TimeSlotEventAdapter(ArrayList<AppointmentModel> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_time_slot, parent, false);

        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        AppointmentModel appointment = appointments.get(position);

        holder.setStartTime(appointment.getStartTime().toString());
        holder.setEndTime(appointment.getEndTime().toString());

        if(appointment.checkIsOccupied()) {
            holder.setUser(appointment.getEmail());
        }
    }

    public void setAppointments(ArrayList<AppointmentModel> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
