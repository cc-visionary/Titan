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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prev_appointments, container, false);

        this.dataAppointments = new PrevAppointmentDataHelper().initializeData();
        this.rvPrevAppointments = view.findViewById(R.id.rv_prev_appointments);
        this.rvPrevAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvPrevAppointments.setAdapter(new PrevAppointmentsAdapter(this.dataAppointments));
        // Inflate the layout for this fragment
        return view;
    }
}