package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.Models.AppointmentModel;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Services.Notification5MinService;
import com.mobdeve.titan.Services.NotificationOnAppointmentService;
import com.mobdeve.titan.Services.NotificationOnDayService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserAddAppointmentActivity extends AppCompatActivity {
    private Spinner daysSpinner, appointmentsSpinner;
    private TextView timeslotsTextView;
    private Button setAppointmentButton;
    private ImageButton backButton;
    private ArrayList<DayModel> days;
    private ArrayList<String> dayStrings, appointmentStrings, allAppointmentStrings;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_appointment);

        this.daysSpinner = findViewById(R.id.s_appointment_day);
        this.appointmentsSpinner = findViewById(R.id.s_appointment_timeslots);
        this.timeslotsTextView = findViewById(R.id.tv_appointment_timeslot);
        this.setAppointmentButton = findViewById(R.id.btn_set_appointment);
        this.backButton = findViewById(R.id.btn_appointment_back);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String eventID = b.getString(String.valueOf(R.string.id_event_id));

        days = new ArrayList<>();
        appointmentStrings = new ArrayList<>();
        allAppointmentStrings = new ArrayList<>();

        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        daysDBHelper.getDaysByEvent(eventID).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                dayStrings = new ArrayList<>();

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    DayModel day = document.toObject(DayModel.class);
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
                    if(day.getUserAppointment(firebaseUser.getEmail()) == null) {
                        String date1 = fmt.format(day.getDate());
                        String date2 = fmt.format(new Date());
                        if(date1.compareTo(date2) >= 0) {
                            days.add(day);
                            dayStrings.add(new SimpleDateFormat("MM/dd/yyyy (EEE)", Locale.getDefault()).format(day.getDate()));
                        }
                    }
                }

                ArrayAdapter daysAdapter = new ArrayAdapter<>(UserAddAppointmentActivity.this, R.layout.item_spinner, dayStrings);
                daysSpinner.setAdapter(daysAdapter);

                if(days.size() > 0) {
                    int availableSlots = 0;
                    for (AppointmentModel appointment : days.get(0).getAppointments()) {
                        allAppointmentStrings.add(String.format("%s - %s", appointment.getStartTime(), appointment.getEndTime()));
                        if(!appointment.checkIsOccupied()) {
                            appointmentStrings.add(String.format("%s - %s", appointment.getStartTime(), appointment.getEndTime()));
                        } else {
                            availableSlots++;
                        }
                    }
                    timeslotsTextView.setText(String.format("Timeslots (%d/%d)", availableSlots, allAppointmentStrings.size()));

                    ArrayAdapter appointmentsAdapter = new ArrayAdapter<>(UserAddAppointmentActivity.this, R.layout.item_spinner, appointmentStrings);
                    appointmentsSpinner.setAdapter(appointmentsAdapter);
                }
            }
        });

       this.daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(days.size() > 0) {
                    appointmentStrings = new ArrayList<>();

                    int availableSlots = 0;
                    for (AppointmentModel appointment : days.get(position).getAppointments()) {
                        if(!appointment.checkIsOccupied()) {
                            appointmentStrings.add(String.format("%s - %s", appointment.getStartTime(), appointment.getEndTime()));
                        } else {
                            availableSlots++;
                        }
                    }
                    timeslotsTextView.setText(String.format("Timeslots (%d/%d)", availableSlots, days.get(position).getAppointments().size()));

                    ArrayAdapter appointmentsAdapter = new ArrayAdapter<>(UserAddAppointmentActivity.this, R.layout.item_spinner, appointmentStrings);
                    appointmentsSpinner.setAdapter(appointmentsAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.setAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle b = intent.getExtras();

                String eventName = b.getString(String.valueOf(R.string.id_event_name));

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                int appointmentIndex = allAppointmentStrings.indexOf(appointmentsSpinner.getSelectedItem());
                DayModel currentDay = days.get(dayStrings.indexOf(daysSpinner.getSelectedItem()));
                currentDay.setAppointment(appointmentIndex, firebaseUser.getEmail());
                daysDBHelper.updateDayAppointments(currentDay.getKey(), currentDay);
                Date date = currentDay.getDate();
                AppointmentModel appointment = currentDay.getAppointments().get(appointmentIndex);
                Calendar firstNotif = Calendar.getInstance();
                firstNotif.set(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
                Calendar secondNotif = Calendar.getInstance();
                secondNotif.set(date.getYear(), date.getMonth(), date.getDate(),  appointment.getStartTime().getHour(), appointment.getStartTime().getMinute(), 0);
                secondNotif.add(Calendar.MINUTE, -5);
                Calendar thirdNotif = Calendar.getInstance();
                thirdNotif.set(date.getYear(), date.getMonth(), date.getDate(),  appointment.getStartTime().getHour(), appointment.getStartTime().getMinute(), 0);
                System.out.println(firstNotif.getTime());
                System.out.println(secondNotif.getTime());
                System.out.println(thirdNotif.getTime());
                scheduleNotificationOnDay(firstNotif.getTimeInMillis());             // 00:00 of the day
                scheduleNotification5MinutesBefore(secondNotif.getTimeInMillis());   // 5 minutes before the apppointment
                scheduleNotificationOnAppointment(thirdNotif.getTimeInMillis());     // on the time of the appointment
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                System.out.println(alarmManager.getNextAlarmClock());
                finish();
            }
        });
    }

    private void scheduleNotificationOnDay(long timeInMillis) {
        Intent notificationIntent = new Intent(UserAddAppointmentActivity.this, NotificationOnDayService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
    }

    private void scheduleNotification5MinutesBefore(long timeInMillis) {
        Intent notificationIntent = new Intent(UserAddAppointmentActivity.this, Notification5MinService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
    }

    private void scheduleNotificationOnAppointment(long timeInMillis) {
        Intent notificationIntent = new Intent(UserAddAppointmentActivity.this, NotificationOnAppointmentService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
    }
}