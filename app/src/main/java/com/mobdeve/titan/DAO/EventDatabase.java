package com.mobdeve.titan.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDatabase extends SQLiteOpenHelper {
    // DB information
    private static final String DATABASE_NAME = "Event.db";
    private static final int DATABASE_VERSION = 1;

    // Column names
    public static final String TABLE_POST = "event_events";
    public static final String EVENT_ID = "user_id";
    public static final String EVENT_EMAIL = "user_email";
    public static final String EVENT_PHONE = "user_phone";
    public static final String EVENT_PASSWORD = "user_password";
    public static final String EVENT_TYPE = "user_type";

    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade_query = "DROP TABLE IF EXISTS " + TABLE_POST;
        db.execSQL(upgrade_query);
    }
}
