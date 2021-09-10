package com.mobdeve.titan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobdeve.titan.Models.UserModel;

import java.util.ArrayList;

public class UserDAO {
    private SQLiteDatabase database;
    private UserDatabase userDatabase;

    public UserDAO(Context context) {
        userDatabase = new UserDatabase(context);
    }

    public boolean addUser(UserModel user) {
        this.database = this.userDatabase.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(UserDatabase.USER_USERNAME, user.getUsername());
        cv.put(UserDatabase.USER_EMAIL, user.getEmail());
        cv.put(UserDatabase.USER_PHONE, user.getNumber());
        cv.put(UserDatabase.USER_PASSWORD, user.getPassword());
        cv.put(UserDatabase.USER_TYPE, user.getUserType());

        long result = this.database.insert(UserDatabase.TABLE_USER, null, cv);
        this.database.close();

        return result != -1;
    }

    public ArrayList<UserModel> getAllUser() {
        String query = "SELECT * FROM " + UserDatabase.TABLE_USER + ";";
        this.database = this.userDatabase.getReadableDatabase();
        ArrayList<UserModel> result = new ArrayList<>();

        Cursor cursor = null;

        if(this.database != null) {
            cursor = this.database.rawQuery(query, null);

            while(cursor.moveToNext()) {
                String username =  cursor.getString(cursor.getColumnIndex(UserDatabase.USER_USERNAME));
                String email = cursor.getString(cursor.getColumnIndex(UserDatabase.USER_EMAIL));
                String phone = cursor.getString(cursor.getColumnIndex(UserDatabase.USER_PHONE));
                String password = cursor.getString(cursor.getColumnIndex(UserDatabase.USER_PASSWORD));
                String userType = cursor.getString(cursor.getColumnIndex(UserDatabase.USER_TYPE));

                result.add(new UserModel(username, email, phone, password, userType));
            }
        }
        this.database.close();

        return result;
    }

    public UserModel getUserByEmail(String email) {
        this.database = this.userDatabase.getReadableDatabase();

        String query = "SELECT * FROM " + UserDatabase.TABLE_USER + " WHERE " + UserDatabase.USER_EMAIL + "=\"" + email + "\"";

        Cursor c = this.database.rawQuery(query, null);

        if(c != null)
            c.moveToFirst();

        String username = c.getString(c.getColumnIndex(UserDatabase.USER_USERNAME));
        String phone = c.getString(c.getColumnIndex(UserDatabase.USER_PHONE));
        String password = c.getString(c.getColumnIndex(UserDatabase.USER_PASSWORD));
        String userType = c.getString(c.getColumnIndex(UserDatabase.USER_TYPE));

        return new UserModel(username, email, phone, password, userType);
    }

    public boolean deleteAllUsers() {
        this.database = this.userDatabase.getWritableDatabase();

        long result = this.database.delete(UserDatabase.TABLE_USER, null, null);

        return result != -1;
    }
}
