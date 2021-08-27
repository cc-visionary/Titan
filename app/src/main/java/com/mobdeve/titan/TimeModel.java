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

    public boolean equals(TimeModel time) {
        return this.hour == time.getHour() && this.minute == time.getMinute();
    }

    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
