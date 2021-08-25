package com.mobdeve.titan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CurrAppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder>{

    private ArrayList<Appointments> dataAppointments;

    public CurrAppointmentsAdapter(ArrayList<Appointments> dataAppointments) { this.dataAppointments = dataAppointments; }

    @NonNull
    @NotNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_appointments, parent, false);

        AppointmentsViewHolder currAppointmentsViewHolder = new AppointmentsViewHolder(view);

        // return custom ViewHolder
        return currAppointmentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppointmentsViewHolder holder, int position) {
        holder.setAppointmentName(this.dataAppointments.get(position).getName());
        holder.setAppointmentDate(this.dataAppointments.get(position).getDate());
        holder.setAppointmentTime(this.dataAppointments.get(position).getTime());
        holder.setStatus(this.dataAppointments.get(position).getStatus());
    }

    @Override
    public int getItemCount() { return this.dataAppointments.size(); }
}
