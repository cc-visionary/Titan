package com.mobdeve.titan;

public class Appointments {
    private String name, date, time;
    private int status;

    public Appointments (String name, String date, String time, int status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getName() { return this.name; }

    public String getDate() { return this.date; }

    public String getTime() { return this.time; }

    public int getStatus() { return this.status; }
}
