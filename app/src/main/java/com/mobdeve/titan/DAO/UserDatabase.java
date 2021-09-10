package com.mobdeve.titan.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    // DB information
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;

    // Column names
    public static final String TABLE_USER = "user_users";
    public static final String USER_ID = "user_id";
    public static final String USER_USERNAME = "user_username";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TYPE = "user_type";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_query = "CREATE TABLE " + TABLE_USER + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_USERNAME + " TEXT, " +
                USER_EMAIL + " TEXT, " +
                USER_PHONE + " TEXT, " +
                USER_PASSWORD + " TEXT, " +
                USER_TYPE +  " TEXT);";

        db.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade_query = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(upgrade_query);
    }
}
