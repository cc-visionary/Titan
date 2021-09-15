package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Models.EventModel;
import com.mobdeve.titan.Models.TimeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AdminEventCreationActivity extends AppCompatActivity {
    private EditText nameEditText, contactEditText, addressEditText, mondayStartEditText, tuesdayStartEditText, wednesdayStartEditText, thursdayStartEditText, fridayStartEditText, mondayEndEditText, tuesdayEndEditText, wednesdayEndEditText, thursdayEndEditText, fridayEndEditText;
    private Spinner mondayMinuteSpinner, tuesdayMinuteSpinner, wednesdayMinuteSpinner, thursdayMinuteSpinner, fridayMinuteSpinner;
    private Button doneButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_creation);

        this.nameEditText = findViewById(R.id.et_event_name);
        this.contactEditText = findViewById(R.id.et_event_contact);
        this.addressEditText = findViewById(R.id.et_event_address);
        this.mondayStartEditText = findViewById(R.id.et_monday_start);
        this.tuesdayStartEditText = findViewById(R.id.et_tuesday_start);
        this.wednesdayStartEditText = findViewById(R.id.et_wednesday_start);
        this.thursdayStartEditText = findViewById(R.id.et_thursday_start);
        this.fridayStartEditText = findViewById(R.id.et_friday_start);
        this.mondayEndEditText = findViewById(R.id.et_monday_end);
        this.tuesdayEndEditText = findViewById(R.id.et_tuesday_end);
        this.wednesdayEndEditText = findViewById(R.id.et_wednesday_end);
        this.thursdayEndEditText = findViewById(R.id.et_thursday_end);
        this.fridayEndEditText = findViewById(R.id.et_friday_end);
        this.mondayMinuteSpinner = findViewById(R.id.s_monday_minute);
        this.tuesdayMinuteSpinner = findViewById(R.id.s_tuesday_minute);
        this.wednesdayMinuteSpinner = findViewById(R.id.s_wednesday_minute);
        this.thursdayMinuteSpinner = findViewById(R.id.s_thursday_minute);
        this.fridayMinuteSpinner = findViewById(R.id.s_friday_minute);
        this.backButton = findViewById(R.id.btn_back_add_event);
        this.doneButton = findViewById(R.id.btn_create_event);

        ArrayList<String> minutes = new ArrayList<>();
        minutes.add("None");
        for(int i = 1; i <= 60; i++) {
            if(60 % i == 0) minutes.add(String.format("%d", i));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(AdminEventCreationActivity.this, R.layout.item_spinner, minutes);
        this.mondayMinuteSpinner.setAdapter(adapter);
        this.tuesdayMinuteSpinner.setAdapter(adapter);
        this.wednesdayMinuteSpinner.setAdapter(adapter);
        this.thursdayMinuteSpinner.setAdapter(adapter);
        this.fridayMinuteSpinner.setAdapter(adapter);

        this.mondayMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(hasAppointments(minutes.get(position))) {
                    mondayStartEditText.setEnabled(true);
                    mondayEndEditText.setEnabled(true);
                } else {
                    mondayStartEditText.setEnabled(false);
                    mondayEndEditText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.tuesdayMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(hasAppointments(minutes.get(position))) {
                    tuesdayStartEditText.setEnabled(true);
                    tuesdayEndEditText.setEnabled(true);
                } else {
                    tuesdayStartEditText.setEnabled(false);
                    tuesdayEndEditText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.wednesdayMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(hasAppointments(minutes.get(position))) {
                    wednesdayStartEditText.setEnabled(true);
                    wednesdayEndEditText.setEnabled(true);
                } else {
                    wednesdayStartEditText.setEnabled(false);
                    wednesdayEndEditText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.thursdayMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(hasAppointments(minutes.get(position))) {
                    thursdayStartEditText.setEnabled(true);
                    thursdayEndEditText.setEnabled(true);
                } else {
                    thursdayStartEditText.setEnabled(false);
                    thursdayEndEditText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.fridayMinuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(hasAppointments(minutes.get(position))) {
                    fridayStartEditText.setEnabled(true);
                    fridayEndEditText.setEnabled(true);
                } else {
                    fridayStartEditText.setEnabled(false);
                    fridayEndEditText.setEnabled(false);
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

        this.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields()) {
                    String name = nameEditText.getText().toString();
                    String contact = contactEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    int mondayMinute = hasAppointments(mondayMinuteSpinner.getSelectedItem().toString()) ? Integer.parseInt(mondayMinuteSpinner.getSelectedItem().toString()) : 0;
                    String[] mondayStart = mondayStartEditText.getText().toString().split(":");
                    String[] mondayEnd = mondayEndEditText.getText().toString().split(":");
                    int tuesdayMinute = hasAppointments(tuesdayMinuteSpinner.getSelectedItem().toString()) ? Integer.parseInt(tuesdayMinuteSpinner.getSelectedItem().toString()) : 0;
                    String[] tuesdayStart = tuesdayStartEditText.getText().toString().split(":");
                    String[] tuesdayEnd = tuesdayEndEditText.getText().toString().split(":");
                    int wednesdayMinute = hasAppointments(wednesdayMinuteSpinner.getSelectedItem().toString()) ? Integer.parseInt(wednesdayMinuteSpinner.getSelectedItem().toString()) : 0;
                    String[] wednesdayStart = wednesdayStartEditText.getText().toString().split(":");
                    String[] wednesdayEnd = wednesdayEndEditText.getText().toString().split(":");
                    int thursdayMinute = hasAppointments(thursdayMinuteSpinner.getSelectedItem().toString()) ? Integer.parseInt(thursdayMinuteSpinner.getSelectedItem().toString()) : 0;
                    String[] thursdayStart = thursdayStartEditText.getText().toString().split(":");
                    String[] thursdayEnd = thursdayEndEditText.getText().toString().split(":");
                    int fridayMinute = hasAppointments(fridayMinuteSpinner.getSelectedItem().toString()) ? Integer.parseInt(fridayMinuteSpinner.getSelectedItem().toString()) : 0;
                    String[] fridayStart = fridayStartEditText.getText().toString().split(":");
                    String[] fridayEnd = fridayEndEditText.getText().toString().split(":");

                    DayModel[] days = {
                            mondayMinute != 0 ? new DayModel(name, new TimeModel(mondayStart[0], mondayStart[1]), new TimeModel(mondayEnd[0], mondayEnd[1]), mondayMinute, Calendar.MONDAY) : null,
                            tuesdayMinute != 0 ? new DayModel(name, new TimeModel(tuesdayStart[0], tuesdayStart[1]), new TimeModel(tuesdayEnd[0], tuesdayEnd[1]), tuesdayMinute, Calendar.TUESDAY) : null,
                            wednesdayMinute != 0 ? new DayModel(name, new TimeModel(wednesdayStart[0], wednesdayStart[1]), new TimeModel(wednesdayEnd[0], wednesdayEnd[1]), wednesdayMinute, Calendar.WEDNESDAY) : null,
                            thursdayMinute != 0 ? new DayModel(name, new TimeModel(thursdayStart[0], thursdayStart[1]), new TimeModel(thursdayEnd[0], thursdayEnd[1]), thursdayMinute, Calendar.THURSDAY) : null,
                            fridayMinute != 0 ? new DayModel(name, new TimeModel(fridayStart[0], fridayStart[1]), new TimeModel(fridayEnd[0], fridayEnd[1]), fridayMinute, Calendar.FRIDAY) : null,
                    };

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    EventModel event = new EventModel(firebaseUser.getEmail(), name, address, contact, Arrays.asList(days));
                    EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();
                    eventDBHelper.addEvent(event);
                    event.addNextMonthToDatabase();
                    finish();
                }
            }
        });
    }

    private boolean hasAppointments(String val) {
        return !val.equals("None");
    }

    private boolean validateFields() {
        String name = nameEditText.getText().toString();
        String contact = contactEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String mondayMinute = mondayMinuteSpinner.getSelectedItem().toString();
        String mondayStart = mondayStartEditText.getText().toString();
        String mondayEnd = mondayEndEditText.getText().toString();
        String tuesdayMinute = tuesdayMinuteSpinner.getSelectedItem().toString();
        String tuesdayStart = tuesdayStartEditText.getText().toString();
        String tuesdayEnd = tuesdayEndEditText.getText().toString();
        String wednesdayMinute = wednesdayMinuteSpinner.getSelectedItem().toString();
        String wednesdayStart = wednesdayStartEditText.getText().toString();
        String wednesdayEnd = wednesdayEndEditText.getText().toString();
        String thursdayMinute = thursdayMinuteSpinner.getSelectedItem().toString();
        String thursdayStart = thursdayStartEditText.getText().toString();
        String thursdayEnd = thursdayEndEditText.getText().toString();
        String fridayMinute = fridayMinuteSpinner.getSelectedItem().toString();
        String fridayStart = fridayStartEditText.getText().toString();
        String fridayEnd = fridayEndEditText.getText().toString();

        boolean isValid = true;

        if(name.isEmpty()) {
            this.nameEditText.setError("Name cannot be empty");
            this.nameEditText.requestFocus();
            isValid = false;
        }

        if(contact.isEmpty()) {
            this.contactEditText.setError("Contact cannot be empty");
            this.contactEditText.requestFocus();
            isValid = false;
        }

        if(address.isEmpty()) {
            this.addressEditText.setError("Address cannot be empty");
            this.addressEditText.requestFocus();
            isValid = false;
        }

        if(hasAppointments(mondayMinute)) {
            if(mondayStart.isEmpty()) {
                this.mondayStartEditText.setError("Monday Start Time cannot be empty");
                isValid = false;
            }

            if(mondayEnd.isEmpty()) {
                this.mondayEndEditText.setError("Monday End Time cannot be empty");
                isValid = false;
            }
        }

        if(hasAppointments(tuesdayMinute)) {
            if(tuesdayStart.isEmpty()) {
                this.tuesdayStartEditText.setError("Tuesday Start Time cannot be empty");
                isValid = false;
            }

            if(tuesdayEnd.isEmpty()) {
                this.tuesdayEndEditText.setError("Tuesday End Time cannot be empty");
                isValid = false;
            }
        }

        if(hasAppointments(wednesdayMinute)) {
            if(wednesdayStart.isEmpty()) {
                this.wednesdayStartEditText.setError("Wednesday Start Time cannot be empty");
                isValid = false;
            }

            if(wednesdayEnd.isEmpty()) {
                this.wednesdayEndEditText.setError("Wednesday End Time cannot be empty");
                isValid = false;
            }
        }

        if(hasAppointments(thursdayMinute)) {
            if(thursdayStart.isEmpty()) {
                this.thursdayStartEditText.setError("Thursday Start Time cannot be empty");
                isValid = false;
            }

            if(thursdayEnd.isEmpty()) {
                this.thursdayEndEditText.setError("Thursday End Time cannot be empty");
                isValid = false;
            }
        }

        if(hasAppointments(fridayMinute)) {
            if(fridayStart.isEmpty()) {
                this.fridayStartEditText.setError("Friday Start Time cannot be empty");
                isValid = false;
            }

            if(fridayEnd.isEmpty()) {
                this.fridayEndEditText.setError("Friday End Time cannot be empty");
                isValid = false;
            }
        }

        return isValid;
    }
}