package com.mobdeve.titan.DatabaseHelpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Models.UserModel;

import java.util.ArrayList;

public class UserDatabaseHelper {
    final private String COLLECTION_NAME = "users";
    private CollectionReference collectionReference;

    public UserDatabaseHelper() {
        collectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public void addUser(UserModel user) {
        collectionReference.document(user.getEmail()).set(user);
    }

    public Task<DocumentSnapshot> getUserByEmail(String email) {
        return collectionReference.document(email).get();
    }

    public Task<QuerySnapshot> getUsers() {
        return collectionReference.get();
    }
}
