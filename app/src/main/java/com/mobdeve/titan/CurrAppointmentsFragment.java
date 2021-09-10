package com.mobdeve.titan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobdeve.titan.Adapters.CurrAppointmentsAdapter;
import com.mobdeve.titan.DataHelpers.PrevAppointmentDataHelper;
import com.mobdeve.titan.Models.Appointments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CurrAppointmentsFragment extends Fragment {

    private TextView tvToday, tvSoon, tvDateToday;
    private RecyclerView rvTodayCurrAppointments;
    private RecyclerView rvSoonCurrAppointments;

    public CurrAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curr_appointments, container, false);

        this.rvTodayCurrAppointments = view.findViewById(R.id.rv_ca_today);
        this.tvToday = view.findViewById(R.id.tv_ca_today);
        this.rvSoonCurrAppointments = view.findViewById(R.id.rv_ca_soon);
        this.tvSoon = view.findViewById(R.id.tv_ca_soon);
        this.tvDateToday = view.findViewById(R.id.tv_ca_date);

        this.tvDateToday.setText(new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date()));

        ArrayList<Appointments> appointments = new PrevAppointmentDataHelper().initializeData();
        this.tvToday.setText(String.format("Today (%d)", appointments.size()));
        this.tvSoon.setText(String.format("Soon (%d)", appointments.size()));
        this.rvTodayCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvTodayCurrAppointments.setAdapter(new CurrAppointmentsAdapter(appointments, true));
        this.rvSoonCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvSoonCurrAppointments.setAdapter(new CurrAppointmentsAdapter(appointments, false));

        // Inflate the layout for this fragment
        return view;
    }
}