package com.mobdeve.titan;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventModel {
    private String creatorUsername, name, address, contactNumber;
    private DayModel[] days;

    public EventModel(String creatorUsername, String name, String address, String contactNumber, DayModel[] days) {
        this.creatorUsername = creatorUsername;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.days = days;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public DayModel[] getDays() {
        return days;
    }

    public String getOpeningHours() {
        final String[] dayNames = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        ArrayList<TimeModel[]> times = new ArrayList<TimeModel[]>();
        ArrayList<ArrayList<Integer>> days = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < this.days.length; i++) {
            if(this.days[i] != null) {
                TimeModel[] currTime = new TimeModel[] {this.days[i].getStartTime(), this.days[i].getEndTime()};
                boolean hasFound = false;
                int foundIndex = -1;
                for(int j = 0; j < times.size() && !hasFound; j++) {
                    if(times.get(j)[0].equals(this.days[i].getStartTime()) && times.get(j)[1].equals(this.days[i].getEndTime())) {
                        hasFound = true;
                        foundIndex = j;
                    }
                }
                if(hasFound && foundIndex != -1) {
                    days.get(foundIndex).add(i);
                } else {
                    times.add(currTime);
                    ArrayList<Integer> currDay = new ArrayList<Integer>();
                    currDay.add(i);
                    days.add(currDay);
                }
            }
        }
        String result = "";
        for(int i = 0; i < days.size(); i++) {
            for(Integer day : days.get(i)) {
                result += dayNames[day] + " ";
            }
            result += String.format("%s - %s", times.get(i)[0], times.get(i)[1]);
            if(i != days.size() - 1) result += "\n";
        }
        return result;
    }

    public boolean isSameCreator(String creatorUsername) {
        return this.creatorUsername == creatorUsername;
    }

    public boolean isToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return this.days[c.get(Calendar.DAY_OF_WEEK)] != null;
    }
}
