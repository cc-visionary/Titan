package com.mobdeve.titan.Models;

public class TimeModel {
    private int hour, minute;

    // hour >= 0 && < 24
    // minute >= 0 && <= 59
    public TimeModel(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public TimeModel(String hour, String minute) {
        this.hour = Integer.parseInt(hour);
        this.minute = Integer.parseInt(minute);
    }

    public TimeModel() {

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
