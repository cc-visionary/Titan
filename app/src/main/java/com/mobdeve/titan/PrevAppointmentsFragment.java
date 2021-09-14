package com.mobdeve.titan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Adapters.PrevAppointmentsAdapter;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.Models.DayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        daysDBHelper.getAllDays().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                ArrayList<AppointmentModel> previousAppointments = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    DayModel day = document.toObject(DayModel.class);
                    if(day.checkIsPast()) {
                        AppointmentModel appointment = day.getUserAppointment(firebaseUser.getEmail());
                        if(appointment != null) {
                            previousAppointments.add(appointment);
                        }
                    }
                }

                rvPrevAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                rvPrevAppointments.setAdapter(new PrevAppointmentsAdapter(previousAppointments));
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}