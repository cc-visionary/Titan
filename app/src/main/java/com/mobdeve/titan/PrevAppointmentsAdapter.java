package com.mobdeve.titan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrevAppointmentsAdapter extends RecyclerView.Adapter<PrevAppointmentsViewHolder> {

    private ArrayList<Appointments> dataAppointments;

    public PrevAppointmentsAdapter(ArrayList<Appointments> dataAppointments) { this.dataAppointments = dataAppointments; }

    @NonNull
    @NotNull
    @Override
    public PrevAppointmentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_prev_appointments, parent, false);

        PrevAppointmentsViewHolder prevAppointmentsViewHolder = new PrevAppointmentsViewHolder(view);

        // return custom ViewHolder
        return prevAppointmentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PrevAppointmentsViewHolder holder, int position) {
        holder.setAppointmentName(this.dataAppointments.get(position).getName());
        holder.setAppointmentDate(this.dataAppointments.get(position).getDate());
        holder.setAppointmentTime(this.dataAppointments.get(position).getTime());
        holder.setStatus(this.dataAppointments.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return this.dataAppointments.size();
    }
}
