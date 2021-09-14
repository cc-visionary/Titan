package com.mobdeve.titan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.ViewHolders.AppointmentsViewHolder;
import com.mobdeve.titan.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CurrAppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder>{

    private ArrayList<AppointmentModel> appointments;
    private boolean isToday;

    public CurrAppointmentsAdapter(ArrayList<AppointmentModel> appointments, boolean isToday) {
        this.appointments = appointments;
        this.isToday = isToday;
    }

    @NonNull
    @NotNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_appointment, parent, false);

        AppointmentsViewHolder currAppointmentsViewHolder = new AppointmentsViewHolder(view);

        return currAppointmentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppointmentsViewHolder holder, int position) {
        AppointmentModel appointment = this.appointments.get(position);
        holder.setName(appointment.getEventName(), isToday);
        holder.setDate(appointment.toStringDate());
        holder.setTime(appointment.toStringTime());

        if(isToday) {
            holder.getCancelButton().setImageResource(R.drawable.today_cancel);
        } else {
            holder.getCancelButton().setImageResource(R.drawable.soon_cancel);
        }

        holder.getCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
                String dayID = appointment.getDayID();
                String email = appointment.getEmail();
                daysDBHelper.getDay(dayID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DayModel day = documentSnapshot.toObject(DayModel.class);
                        day.setAppointment(day.getAppointments().indexOf(day.getUserAppointment(email)), null);
                        daysDBHelper.updateDayAppointments(dayID, day);
                        appointments.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() { return this.appointments.size(); }
}
