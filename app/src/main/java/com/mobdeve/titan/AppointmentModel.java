package com.mobdeve.titan;

import java.util.Date;

public class AppointmentModel {
    private String username, status;
    private TimeModel startTime, endTime;

    // Creates a Appointment providing all information
    public AppointmentModel(String username, TimeModel startTime, TimeModel endTime, String status) {
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // Creates a Appointment without the username (appointment isn't taken yet)
    public AppointmentModel(TimeModel startTime, TimeModel endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TimeModel getStartTime() {
        return startTime;
    }

    public TimeModel getEndTime() {
        return endTime;
    }

    public boolean isOccupied() {
        return !username.isEmpty();
    }
}
