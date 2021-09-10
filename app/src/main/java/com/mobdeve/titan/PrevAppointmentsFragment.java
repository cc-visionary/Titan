package com.mobdeve.titan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobdeve.titan.Adapters.PrevAppointmentsAdapter;
import com.mobdeve.titan.DataHelpers.PrevAppointmentDataHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrevAppointmentsFragment extends Fragment {

    private RecyclerView rvPrevAppointments;
    private TextView tvDateToday;

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

        this.rvPrevAppointments = view.findViewById(R.id.rv_prev_appointments);
        this.tvDateToday = view.findViewById(R.id.tv_pa_date);

        this.tvDateToday.setText(new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date()));

        this.rvPrevAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        this.rvPrevAppointments.setAdapter(new PrevAppointmentsAdapter(new PrevAppointmentDataHelper().initializeData()));

        // Inflate the layout for this fragment
        return view;
    }
}