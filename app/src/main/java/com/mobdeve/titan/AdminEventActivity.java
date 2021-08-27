package com.mobdeve.titan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdminEventActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnCancel;
    private TextView tvTitle, tvAddress, tvOpeningHours;
    private RecyclerView rvTimeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event);

        this.btnBack = findViewById(R.id.btn_back_event);
        this.btnCancel = findViewById(R.id.btn_cancel_event);
        this.tvTitle = findViewById(R.id.tv_event_title);
        this.tvAddress = findViewById(R.id.tv_address);
        this.tvOpeningHours = findViewById(R.id.tv_opening_hours);
        this.rvTimeSlots = findViewById(R.id.rv_time_slots);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        this.tvTitle.setText(b.getString(String.valueOf(R.string.id_event_name)));
        this.tvOpeningHours.setText(b.getString(String.valueOf(R.string.id_event_schedule)));
        this.tvAddress.setText(b.getString(String.valueOf(R.string.id_event_address)));

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}