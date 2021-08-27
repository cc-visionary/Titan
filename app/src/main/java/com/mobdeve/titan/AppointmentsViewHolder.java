package com.mobdeve.titan;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class AppointmentsViewHolder extends RecyclerView.ViewHolder {

    private TextView tvAppointmentName;
    private TextView tvAppointmentDate;
    private TextView tvAppointmentTime;
    private ImageView ivStatus;

    public AppointmentsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.tvAppointmentName = itemView.findViewById(R.id.tv_appointment_name);
        this.tvAppointmentDate = itemView.findViewById(R.id.tv_appointment_date);
        this.tvAppointmentTime = itemView.findViewById(R.id.tv_appointment_time);
        this.ivStatus = itemView.findViewById(R.id.iv_status);
    }

    public void setName(String name, boolean isToday) {
        if(isToday) this.tvAppointmentName.setTextColor(Color.parseColor("#83C5BE"));
        this.tvAppointmentName.setText(name);
    }

    public void setDate(String date) { this.tvAppointmentDate.setText(date); }

    public void setTime(String time) { this.tvAppointmentTime.setText(time); }

    public void setStatus(int status) { this.ivStatus.setImageResource(status); }
}
