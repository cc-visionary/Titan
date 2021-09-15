package com.mobdeve.titan.ViewHolders;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.titan.R;

import org.jetbrains.annotations.NotNull;

public class AppointmentsViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView, dateTextView, timeTextView;
    private ImageButton cancelButton;

    public AppointmentsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.nameTextView = itemView.findViewById(R.id.tv_appointment_name);
        this.dateTextView = itemView.findViewById(R.id.tv_appointment_date);
        this.timeTextView = itemView.findViewById(R.id.tv_appointment_time);
        this.cancelButton = itemView.findViewById(R.id.btn_appointment_cancel);
    }

    public void setName(String name, boolean isToday) {
        if(isToday) this.nameTextView.setTextColor(Color.parseColor("#83C5BE"));
        this.nameTextView.setText(name);
    }

    public void setDate(String date) { this.dateTextView.setText(date); }

    public void setTime(String time) { this.timeTextView.setText(time); }

    public ImageButton getCancelButton() {
        return cancelButton;
    }
}
