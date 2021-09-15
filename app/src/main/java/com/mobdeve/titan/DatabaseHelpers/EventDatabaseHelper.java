package com.mobdeve.titan.DatabaseHelpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Models.EventModel;

public class EventDatabaseHelper {
    final private String COLLECTION_NAME = "events";
    private CollectionReference collectionReference;

    public EventDatabaseHelper() {
        collectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public Task<Void> addEvent(EventModel event) {
        return collectionReference.document(event.getKey()).set(event);
    }

    public Task<QuerySnapshot> getEventsByEmail(String email) {
        return collectionReference.whereEqualTo("creatorEmail", email).get();
    }

    public Task<QuerySnapshot> getAllEvents() {
        return collectionReference.get();
    }

    public Task<DocumentSnapshot> getEvent(String eventID) {
        return collectionReference.document(eventID).get();
    }

    public Task<Void> deleteEvent(String eventID) {
        return collectionReference.document(eventID).delete();
    }
}
