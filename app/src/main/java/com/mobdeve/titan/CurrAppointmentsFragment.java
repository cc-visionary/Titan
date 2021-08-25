package com.mobdeve.titan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CurrAppointmentsFragment extends Fragment {

    private RecyclerView rvTodayCurrAppointments;
    private RecyclerView rvSoonCurrAppointments;
    private ArrayList<Appointments> dataAppointments;

    public CurrAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dataAppointments = new PrevAppointmentDataHelper().initializeData();
        this.rvTodayCurrAppointments.findViewById(R.id.rv_today_curr_appointments);
        //TODO pls fix
        //this.rvTodayCurrAppointments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvTodayCurrAppointments.setAdapter(new CurrAppointmentsAdapter(this.dataAppointments));
        this.rvSoonCurrAppointments.findViewById(R.id.rv_soon_curr_appointmnets);
        //TODO pls fix
        //this.rvSoonCurrAppointments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvSoonCurrAppointments.setAdapter(new CurrAppointmentsAdapter(this.dataAppointments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curr_appointments, container, false);
    }
}