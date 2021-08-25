package com.mobdeve.titan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PrevAppointmentsFragment extends Fragment {

    private RecyclerView rvPrevAppointments;
    private ArrayList<Appointments> dataAppointments;

    public PrevAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.dataAppointments = new PrevAppointmentDataHelper().initializeData();
        this.rvPrevAppointments.findViewById(R.id.rv_prev_appointments);
        //TODO pls fix
        //this.rvPrevAppointments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvPrevAppointments.setAdapter(new PrevAppointmentsAdapter(this.dataAppointments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prev_appointments, container, false);

    }
}