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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curr_appointments, container, false);

        this.dataAppointments = new PrevAppointmentDataHelper().initializeData();
        this.rvTodayCurrAppointments = view.findViewById(R.id.rv_today_curr_appointments);
        this.rvTodayCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvTodayCurrAppointments.setAdapter(new CurrAppointmentsAdapter(this.dataAppointments));
        this.rvSoonCurrAppointments = view.findViewById(R.id.rv_soon_curr_appointmnets);
        this.rvSoonCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvSoonCurrAppointments.setAdapter(new CurrAppointmentsAdapter(this.dataAppointments));
        // Inflate the layout for this fragment
        return view;
    }
}