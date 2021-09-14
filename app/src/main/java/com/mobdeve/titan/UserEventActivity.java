package com.mobdeve.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;
import com.mobdeve.titan.Models.DayModel;

public class UserEventActivity extends AppCompatActivity {
    private ImageButton backButton, callButton, messageButton;
    private Button makeAppointmentButton;
    private TextView titleTextView, addressTextView, openingHoursTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event);

        this.titleTextView = findViewById(R.id.tv_user_event_title);
        this.addressTextView = findViewById(R.id.tv_user_address);
        this.openingHoursTextView = findViewById(R.id.tv_user_opening_hours);
        this.backButton = findViewById(R.id.btn_user_back_event);
        this.callButton = findViewById(R.id.btn_user_event_call);
        this.messageButton = findViewById(R.id.btn_user_event_message);
        this.makeAppointmentButton = findViewById(R.id.btn_user_make_appointment);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        this.titleTextView.setText(b.getString(String.valueOf(R.string.id_event_name)));
        this.openingHoursTextView.setText(b.getString(String.valueOf(R.string.id_event_schedule)));
        this.addressTextView.setText(b.getString(String.valueOf(R.string.id_event_address)));

        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        daysDBHelper.getDaysByEvent(b.getString(String.valueOf(R.string.id_event_id))).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                int appointmentsAttended = 0;
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    DayModel day = document.toObject(DayModel.class);
                    if(day.getUserAppointment(firebaseUser.getEmail()) != null) appointmentsAttended++;
                }
                if(appointmentsAttended != 0 && appointmentsAttended == queryDocumentSnapshots.size()) {
                    makeAppointmentButton.setEnabled(false);
                    makeAppointmentButton.setClickable(false);
                } else {
                    makeAppointmentButton.setEnabled(true);
                    makeAppointmentButton.setClickable(true);
                }
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String eventContact = b.getString(String.valueOf(R.string.id_event_contact));

        this.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(String.format("tel:%s", eventContact)));
                startActivity(intent);
            }
        });

        this.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.fromParts("sms", eventContact, null));
                startActivity(intent);
            }
        });

        this.makeAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appointmentIntent = new Intent(UserEventActivity.this, UserAddAppointmentActivity.class);
                appointmentIntent.putExtra(String.valueOf(R.string.id_event_id), b.getString(String.valueOf(R.string.id_event_id)));
                startActivity(appointmentIntent);
            }
        });
    }
}