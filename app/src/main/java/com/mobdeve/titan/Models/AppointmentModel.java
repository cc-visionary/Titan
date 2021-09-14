package com.mobdeve.titan.Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentModel {
    private Date date;
    private String eventName, email, dayID;
    private TimeModel startTime, endTime;

    // Creates a Appointment without the username (appointment isn't taken yet)
    public AppointmentModel(String eventName, TimeModel startTime, TimeModel endTime) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AppointmentModel() {

    }

    public String getEventName() {
        return eventName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TimeModel getStartTime() {
        return startTime;
    }

    public TimeModel getEndTime() {
        return endTime;
    }

    public String getDayID() {
        return dayID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public String toStringDate() {
        if(date != null) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            return format.format(date);
        }
        return "[Unknown Date]";
    }

    public String toStringTime() {
        return String.format("%s - %s", startTime, endTime);
    }

    public boolean checkIsOccupied() {
        return email != null && !email.isEmpty();
    }
}
