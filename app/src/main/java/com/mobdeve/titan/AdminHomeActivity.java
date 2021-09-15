package com.mobdeve.titan;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Adapters.EventAdapter;
import com.mobdeve.titan.DataHelpers.EventDataHelper;
import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Models.EventModel;
import com.mobdeve.titan.Models.TimeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminHomeActivity extends AppCompatActivity {
    private TextView tvDate, tvTodayLabel, tvSoonLabel;
    private RecyclerView rvToday, rvSoon;
    private ImageButton btnLogout;
    private FloatingActionButton btnAddEvent;
    private EventAdapter todayAdapter, soonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        this.tvDate = findViewById(R.id.tv_date);
        this.tvTodayLabel = findViewById(R.id.tv_today_label);
        this.tvSoonLabel = findViewById(R.id.tv_soon_label);
        this.rvToday = findViewById(R.id.rv_today_events);
        this.rvSoon = findViewById(R.id.rv_soon_events);
        this.btnLogout = findViewById(R.id.btn_logout);
        this.btnAddEvent = findViewById(R.id.btn_add_event);

        // set the date to the date today
        this.tvDate.setText(new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date()));

        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });

        this.btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdminEventCreationActivity.class);
                startActivity(intent);
            }
        });

        this.initRecyclerView();
    }

    private void initRecyclerView() {
        EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();

//         EventDataHelper.generateEventDatabase();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        eventDBHelper.getEventsByEmail(firebaseUser.getEmail()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    ArrayList<EventModel> eventsToday = new ArrayList<EventModel>();
                    ArrayList<EventModel> eventsSoon = new ArrayList<EventModel>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        EventModel eventModel = document.toObject(EventModel.class);
                        if(eventModel.checkIsToday()) {
                            eventsToday.add(eventModel);
                        } else {
                            eventsSoon.add(eventModel);
                        }
                    }

                    todayAdapter = new EventAdapter(eventsToday, true);
                    soonAdapter = new EventAdapter(eventsSoon, false);

                    RecyclerView.LayoutManager todayManager = new LinearLayoutManager(AdminHomeActivity.this, LinearLayoutManager.VERTICAL, false);
                    RecyclerView.LayoutManager soonManager = new LinearLayoutManager(AdminHomeActivity.this, LinearLayoutManager.VERTICAL, false);

                    tvTodayLabel.setText(String.format("Today (%d)", eventsToday.size()));
                    tvSoonLabel.setText(String.format("Soon (%d)", eventsSoon.size()));

                    rvToday.setAdapter(todayAdapter);
                    rvToday.setLayoutManager(todayManager);
                    rvSoon.setAdapter(soonAdapter);
                    rvSoon.setLayoutManager(soonManager);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.initRecyclerView();
    }
}