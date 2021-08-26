package com.mobdeve.titan;

public class TimeModel {
    private int hour, minute;

    // hour >= 0 && <= 24
    // minute >= 0 && <= 59
    public TimeModel(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String toString() {
        return String.format("%2d:%2d", hour, minute);
    }
}
