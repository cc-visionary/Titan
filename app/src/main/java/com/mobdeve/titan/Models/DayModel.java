package com.mobdeve.titan.Models;

import java.util.ArrayList;

// to be used in EventModel
public class DayModel {
    private int minutePerSession;
    private TimeModel startTime, endTime;
    private ArrayList<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

    public DayModel(TimeModel startTime, TimeModel endTime, int minutePerSession) {
        this.minutePerSession = minutePerSession;
        this.startTime = startTime;
        this.endTime = endTime;

        for(int currHour = this.startTime.getHour(); currHour < this.endTime.getHour(); currHour++) {
            for(int currMinute = currHour == this.startTime.getHour() ? this.startTime.getMinute() : 0; currMinute < (currHour == this.endTime.getHour() ? this.endTime.getMinute() : 60); currMinute += this.minutePerSession) {
                if(currMinute + this.minutePerSession >= 60) {
                    // if added current + added minute is greater or equal to 60
                    appointments.add(new AppointmentModel(new TimeModel(currHour, currMinute), new TimeModel(currHour + 1, currMinute + this.minutePerSession - 60)));
                } else {
                    appointments.add(new AppointmentModel(new TimeModel(currHour, currMinute), new TimeModel(currHour, currMinute + this.minutePerSession)));
                }
            }
        }
    }

    public TimeModel getStartTime() {
        return startTime;
    }

    public TimeModel getEndTime() {
        return endTime;
    }

    public boolean isClosed() {
        return startTime.getHour() == 0 && startTime.getMinute() == 0 && endTime.getHour() == 0 && endTime.getMinute() == 0;
    }
}
