package com.mobdeve.titan;

import java.util.Calendar;
import java.util.Date;

public class EventModel {
    private int eventId, creatorId;
    private String name, address, contactNumber;
    private DayModel[] days;

    public EventModel(int eventId, int creatorId, String name, String address, String contactNumber, DayModel[] days) {
        this.eventId = eventId;
        this.creatorId = creatorId;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.days = days;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCreatorId() {
        return creatorId;
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
        return "";
    }

    public boolean isSameCreator(int creatorId) {
        return this.creatorId == creatorId;
    }

    public boolean isToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return this.days[c.get(Calendar.DAY_OF_WEEK)] != null;
    }
}
