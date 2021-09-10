package com.mobdeve.titan.DatabaseHelpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.titan.Models.UserModel;

import java.util.ArrayList;

public class UserDatabaseHelper {
    final public static String COLLECTION_NAME = "users";
    private FirebaseFirestore db;

    public UserDatabaseHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(UserModel user) {
        db.collection(COLLECTION_NAME).document(user.getEmail()).set(user);
    }

    public Task<DocumentSnapshot> getUserByEmail(String email) {
        return db.collection(COLLECTION_NAME).document(email).get();
    }

    public Task<QuerySnapshot> getUsers() {
        return db.collection(COLLECTION_NAME).get();
    }
}
