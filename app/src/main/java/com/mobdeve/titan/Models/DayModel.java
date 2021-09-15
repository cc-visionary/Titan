package com.mobdeve.titan.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// to be used in EventModel
public class DayModel {
    private String eventID, key;
    private int minutePerSession, day;
    private TimeModel startTime, endTime;
    private Date date;
    private ArrayList<AppointmentModel> appointments = new ArrayList<AppointmentModel>();

    public DayModel(String eventName, TimeModel startTime, TimeModel endTime, int minutePerSession, int day) {
        this.minutePerSession = minutePerSession;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;

        for(int currHour = this.startTime.getHour(); currHour < this.endTime.getHour(); currHour++) {
            for(int currMinute = currHour == this.startTime.getHour() ? this.startTime.getMinute() : 0; currMinute < (currHour == this.endTime.getHour() ? this.endTime.getMinute() : 60); currMinute += this.minutePerSession) {
                if(currMinute + this.minutePerSession >= 60) {
                    // if added current + added minute is greater or equal to 60
                    appointments.add(new AppointmentModel(eventName, new TimeModel(currHour, currMinute), new TimeModel(currHour + 1, currMinute + this.minutePerSession - 60)));
                } else {
                    appointments.add(new AppointmentModel(eventName, new TimeModel(currHour, currMinute), new TimeModel(currHour, currMinute + this.minutePerSession)));
                }
            }
        }
    }

    public DayModel() {

    }

    public int getDay() {
        return day;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public TimeModel getStartTime() {
        return startTime;
    }

    public TimeModel getEndTime() {
        return endTime;
    }

    public ArrayList<AppointmentModel> getAppointments() {
        return appointments;
    }

    public Date findNextDate(int week) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        cal.set(Calendar.DAY_OF_WEEK, day);
        if(!fmt.format(cal.getTime()).equals(fmt.format(new Date())) || week > 1) {
            cal.add(Calendar.DATE, 7 * week);
        }
        setDate(cal.getTime());
        return cal.getTime();
    }

    public void setAppointment(int index, String email) {
        appointments.get(index).setEmail(email);
    }

    public AppointmentModel getUserAppointment(String email) {
        AppointmentModel userAppointment = null;
        for(AppointmentModel appointment : getAppointments()) {
            if(appointment.getEmail() != null) {
                if(appointment.getEmail().equals(email)) userAppointment = appointment;
            }
        }
        return userAppointment;
    }

    public void setAppointmentDetails(Date date, String dayID) {
        for(AppointmentModel appointment : getAppointments()) {
            appointment.setDate(date);
            appointment.setDayID(dayID);
        }
    }

    public boolean checkIsPast() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String dateToday = fmt.format(new Date());
        String dateCompared = fmt.format(getDate());
        return dateToday.compareTo(dateCompared) > 0;
    }

    public boolean checkIsToday() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String dateToday = fmt.format(new Date());
        String dateCompared = fmt.format(getDate());
        return dateToday.equals(dateCompared);
    }
}
