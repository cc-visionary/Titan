package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.titan.Adapters.EventAdapter;
import com.mobdeve.titan.DataHelpers.EventDataHelper;
import com.mobdeve.titan.Models.EventModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminHomeActivity extends AppCompatActivity {
    private TextView tvDate, tvTodayLabel, tvSoonLabel;
    private RecyclerView rvToday, rvSoon;
    private ImageButton btnLogout;
    private FloatingActionButton btnAddEvent;

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
                v.getContext().startActivity(intent);
            }
        });

        this.initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<EventModel> eventsToday = new ArrayList<EventModel>();
        ArrayList<EventModel> eventsSoon = new ArrayList<EventModel>();

        for(EventModel eventModel : EventDataHelper.loadEventData()) {
            if(eventModel.isToday()) eventsToday.add(eventModel);
            else eventsSoon.add(eventModel);
        }

        EventAdapter todayAdapter = new EventAdapter(eventsToday, true);
        EventAdapter soonAdapter = new EventAdapter(eventsSoon, false);

        RecyclerView.LayoutManager todayManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager soonManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.tvTodayLabel.setText(String.format("Today (%d)", eventsToday.size()));
        this.tvSoonLabel.setText(String.format("Soon (%d)", eventsSoon.size()));

        this.rvToday.setAdapter(todayAdapter);
        this.rvToday.setLayoutManager(todayManager);
        this.rvSoon.setAdapter(soonAdapter);
        this.rvSoon.setLayoutManager(soonManager);
    }
}