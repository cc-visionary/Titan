package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Adapters.TimeSlotEventAdapter;
import com.mobdeve.titan.DataHelpers.EventDataHelper;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Models.EventModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminEventActivity extends AppCompatActivity {
    private ImageButton backButton;
    private Button deleteButton, generateButton;
    private TextView titleTextView, addressTextView, openingHoursTextView, timeslotTextView;
    private Spinner daysSpinner;
    private RecyclerView timeSlotsRecyclerView;
    private ArrayList<DayModel> days;
    private TimeSlotEventAdapter timeSlotEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event);

        this.backButton = findViewById(R.id.btn_host_back_event);
        this.deleteButton = findViewById(R.id.btn_host_delete_event);
        this.generateButton = findViewById(R.id.btn_host_generate);
        this.titleTextView = findViewById(R.id.tv_host_event_title);
        this.addressTextView = findViewById(R.id.tv_host_address);
        this.openingHoursTextView = findViewById(R.id.tv_host_opening_hours);
        this.timeslotTextView = findViewById(R.id.tv_host_time_slot_title);
        this.timeSlotsRecyclerView = findViewById(R.id.rv_host_time_slots);
        this.daysSpinner = findViewById(R.id.s_admin_timeslot_days);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String eventID = b.getString(String.valueOf(R.string.id_event_id));

        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        days = new ArrayList<>();

        daysDBHelper.getDaysByEvent(eventID).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> daysString = new ArrayList<>();

                for(QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    DayModel day = document.toObject(DayModel.class);
                    days.add(day);
                    daysString.add(new SimpleDateFormat("MM/dd/yyyy (EEE)", Locale.getDefault()).format(day.getDate()));
                }

                if(days.size() > 0) {
                    int availableSlots = 0;
                    for (AppointmentModel appointment : days.get(0).getAppointments()) {
                        if(appointment.checkIsOccupied()) availableSlots++;
                    }
                    timeslotTextView.setText(String.format("Timeslots (%d/%d)", availableSlots, days.get(0).getAppointments().size()));

                    timeSlotEventAdapter = new TimeSlotEventAdapter(days.get(0).getAppointments());
                    timeSlotsRecyclerView.setLayoutManager(new LinearLayoutManager(AdminEventActivity.this, LinearLayoutManager.VERTICAL, false));
                    timeSlotsRecyclerView.setAdapter(timeSlotEventAdapter);
                }

                ArrayAdapter adapter = new ArrayAdapter<>(AdminEventActivity.this, R.layout.item_spinner, daysString);
                daysSpinner.setAdapter(adapter);
            }
        });
        this.titleTextView.setText(b.getString(String.valueOf(R.string.id_event_name)));
        this.openingHoursTextView.setText(b.getString(String.valueOf(R.string.id_event_schedule)));
        this.addressTextView.setText(b.getString(String.valueOf(R.string.id_event_address)));

        this.daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int availableSlots = 0;
                for (AppointmentModel appointment : days.get(position).getAppointments()) {
                    if(appointment.checkIsOccupied()) availableSlots++;
                }
                timeslotTextView.setText(String.format("Timeslots (%d/%d)", availableSlots, days.get(position).getAppointments().size()));

                timeSlotEventAdapter.setAppointments(days.get(position).getAppointments());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();
                DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
                System.out.println(eventID);
                eventDBHelper.deleteEvent(eventID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        daysDBHelper.getDaysByEvent(eventID).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    DayModel day = document.toObject(DayModel.class);
                                    System.out.println(day.getKey());
                                    daysDBHelper.deleteDay(day.getKey());
                                }
                                finish();
                            }
                        });
                    }
                });
            }
        });

        this.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();
                eventDBHelper.getEvent(eventID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        EventModel event = documentSnapshot.toObject(EventModel.class);
                        event.addNextMonthToDatabase();
                    }
                });
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}