package com.mobdeve.titan;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.mobdeve.titan.Adapters.CurrAppointmentsAdapter;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.Models.DayModel;

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

        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        daysDBHelper.getAllDays().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                ArrayList<AppointmentModel> appointmentsToday = new ArrayList<>();
                ArrayList<AppointmentModel> appointmentsSoon = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    DayModel day = document.toObject(DayModel.class);
                    if(!day.checkIsPast()) {
                        AppointmentModel appointment = day.getUserAppointment(firebaseUser.getEmail());
                        if(appointment != null) {
                            if(day.checkIsToday()) {
                                appointmentsToday.add(appointment);
                            } else {
                                appointmentsSoon.add(appointment);
                            }
                        }
                    }
                }
                tvToday.setText(String.format("Today (%d)", appointmentsToday.size()));
                tvSoon.setText(String.format("Soon (%d)", appointmentsSoon.size()));

                appointmentsSoon.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                appointmentsToday.sort((o1, o2) -> o1.getStartTime().toString().compareTo(o2.getStartTime().toString()));

                rvTodayCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                rvTodayCurrAppointments.setAdapter(new CurrAppointmentsAdapter(appointmentsToday, true));
                rvSoonCurrAppointments.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                rvSoonCurrAppointments.setAdapter(new CurrAppointmentsAdapter(appointmentsSoon, false));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}