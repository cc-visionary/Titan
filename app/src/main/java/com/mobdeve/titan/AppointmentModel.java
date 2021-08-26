package com.mobdeve.titan;

import java.util.Date;

public class AppointmentModel {
    private int userId;
    private TimeModel startTime, endTime;

    // Creates a Appointment providing all information
    public AppointmentModel(int userId, TimeModel startTime, TimeModel endTime) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Creates a Appointment without the userId (appointment isn't taken yet)
    public AppointmentModel(TimeModel startTime, TimeModel endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public TimeModel getStartTime() {
        return startTime;
    }

    public TimeModel getEndTime() {
        return endTime;
    }

    public boolean isOccupied() {
        return userId == 0;
    }
}
