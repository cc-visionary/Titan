package com.mobdeve.titan;

public class event
{
    int slots,timeAlloted;

    String startTime, endTime, eventName;

    public event(String eventName, String startTime, String endTime, int slots, int time)
    {
        this.eventName=eventName;
        this.startTime=startTime;
        this.endTime=endTime;
        this.slots=slots;
        this.timeAlloted=time;
    }

    public int getSlots() {
        return slots;
    }

    public int gettimeAlloted() {
        return timeAlloted;
    }

    public String getEventName() {
        return eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
