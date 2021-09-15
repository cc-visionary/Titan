package com.mobdeve.titan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.ViewHolders.AppointmentsViewHolder;
import com.mobdeve.titan.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrevAppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder> {

    private ArrayList<AppointmentModel> appointments;

    public PrevAppointmentsAdapter(ArrayList<AppointmentModel> appointments) { this.appointments = appointments; }

    @NonNull
    @NotNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_appointment, parent, false);

        AppointmentsViewHolder prevAppointmentsViewHolder = new AppointmentsViewHolder(view);

        // return custom ViewHolder
        return prevAppointmentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppointmentsViewHolder holder, int position) {
        AppointmentModel appointment = this.appointments.get(position);
        holder.setName(appointment.getEventName(), false);
        holder.setDate(appointment.toStringDate());
        holder.setTime(appointment.toStringTime());
    }

    @Override
    public int getItemCount() {
        return this.appointments.size();
    }
}
