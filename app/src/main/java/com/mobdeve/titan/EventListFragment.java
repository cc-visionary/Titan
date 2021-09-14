package com.mobdeve.titan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Adapters.EventAdapter;
import com.mobdeve.titan.Adapters.EventListAdapter;
import com.mobdeve.titan.DataHelpers.EventDataHelper;
import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.EventModel;

import java.util.ArrayList;

public class EventListFragment extends Fragment {
    private EditText searchEditText;
    private ImageButton searchButton;
    private RecyclerView recyclerView;
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

        return view;
    }
}