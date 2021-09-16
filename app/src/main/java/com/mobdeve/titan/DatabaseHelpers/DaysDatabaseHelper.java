package com.mobdeve.titan.DatabaseHelpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Models.DayModel;

public class DaysDatabaseHelper {
    final private String COLLECTION_NAME = "days";
    private CollectionReference collectionReference;

    public DaysDatabaseHelper() {
        collectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void addDays(String key, DayModel day) {
        collectionReference.document(key).set(day);
    }

    public void updateDayAppointments(String dayID, DayModel dayModel) {
        collectionReference.document(dayID).update("appointments", dayModel.getAppointments());
    }

    public Task<DocumentSnapshot> getDay(String dayID) {
        return collectionReference.document(dayID).get();
    }

    public Task<QuerySnapshot> getAllDays() {
        return collectionReference.get();
    }

    public Task<QuerySnapshot> getDaysByEvent(String eventID) {
        return collectionReference.whereEqualTo("eventID", eventID).get();
    }

    public void deleteDay(String dayID) {
        collectionReference.document(dayID).delete();
    }
}
