package com.mobdeve.titan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Adapters.EventAdapter;
import com.mobdeve.titan.Adapters.EventListAdapter;
import com.mobdeve.titan.DataHelpers.EventDataHelper;
import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Models.EventModel;

import java.util.ArrayList;

public class EventListFragment extends Fragment {
    private EditText searchEditText;
    private ImageButton searchButton;
    private RecyclerView recyclerView;
    private Spinner daysFilterSpinner;
    EventListAdapter eventListAdapter;
    ArrayList<EventModel> events;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        this.searchEditText = view.findViewById(R.id.et_event_search);
        this.searchButton = view.findViewById(R.id.btn_search);
        this.daysFilterSpinner = view.findViewById(R.id.s_days_filter);
        this.recyclerView = view.findViewById(R.id.rv_event_list);

        events = new ArrayList<EventModel>();

        EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();
//        EventDataHelper.generateEventDatabase();
        eventDBHelper.getAllEvents().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    EventModel event = document.toObject(EventModel.class);
                    events.add(event);
                }

                eventListAdapter = new EventListAdapter(events);

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(eventListAdapter);
            }
        });

        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEditText.getText().toString().isEmpty()) {
                    eventListAdapter.setEvents(events);
                } else {
                    ArrayList<EventModel> filteredEvents = new ArrayList<>();
                    for(EventModel event : events) {
                        if(event.getName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase())) {
                            filteredEvents.add(event);
                        }
                    }
                    eventListAdapter.setEvents(filteredEvents);
                }
            }
        });
        String[] days = new String[]{"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Everyday"};
        ArrayAdapter filterAdapter = new ArrayAdapter<>(view.getContext(), R.layout.item_spinner, days);
        this.daysFilterSpinner.setAdapter(filterAdapter);
        this.daysFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    // all the appointments (default)
                    if(eventListAdapter != null) eventListAdapter.setEvents(events);
                } else if(position == 6) {
                    // only add if event is taking appointments everyday
                    ArrayList<EventModel> filteredEvents = new ArrayList<>();
                    for(EventModel event : events) {
                        boolean isEveryday = true;
                        for(DayModel day : event.getDays()) {
                            if(day == null) {
                                isEveryday = false;
                            }
                        }
                        if(isEveryday) {
                            filteredEvents.add(event);
                        }
                    }
                    eventListAdapter.setEvents(filteredEvents);
                } else {
                    ArrayList<EventModel> filteredEvents = new ArrayList<>();
                    for(EventModel event : events) {
                        if(event.getDays().get(position - 1) != null) {
                            filteredEvents.add(event);
                        }
                    }
                    eventListAdapter.setEvents(filteredEvents);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}