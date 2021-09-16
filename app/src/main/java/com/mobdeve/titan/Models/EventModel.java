package com.mobdeve.titan.Models;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mobdeve.titan.DatabaseHelpers.DaysDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventModel {
    public static String EVENT_NAME_KEY = "EVENT_NAME";
    public static String EVENT_CONTACT_KEY = "EVENT_CONTACT";
    public static String EVENT_ADDRESS_KEY = "EVENT_ADDRESS";
    public static String MONDAY_START_KEY = "MONDAY_START";
    public static String MONDAY_END_KEY = "MONDAY_END";
    public static String MONDAY_MINUTE_KEY = "MONDAY_MINUTE";
    public static String TUESDAY_START_KEY = "TUESDAY_START";
    public static String TUESDAY_END_KEY = "TUESDAY_END";
    public static String TUESDAY_MINUTE_KEY = "TUESDAY_MINUTE";
    public static String WEDNESDAY_START_KEY = "WEDNESDAY_START";
    public static String WEDNESDAY_END_KEY = "WEDNESDAY_END";
    public static String WEDNESDAY_MINUTE_KEY = "WEDNESDAY_MINUTE";
    public static String THURSDAY_START_KEY = "THURSDAY_START";
    public static String THURSDAY_END_KEY = "THURSDAY_END";
    public static String THURSDAY_MINUTE_KEY = "THURSDAY_MINUTE";
    public static String FRIDAY_START_KEY = "FRIDAY_START";
    public static String FRIDAY_END_KEY = "FRIDAY_END";
    public static String FRIDAY_MINUTE_KEY = "FRIDAY_MINUTE";

    private String key, creatorEmail, name, address, contactNumber;
    private List<DayModel> days;

    public EventModel(String creatorEmail, String name, String address, String contactNumber, List<DayModel> days) {
        this.creatorEmail = creatorEmail;
        this.name = name;
        this.key = creatorEmail.split("@")[0] + "_" + name.toLowerCase();
        this.address = address;
        this.contactNumber = contactNumber;
        this.days = days;
    }

    public EventModel() {

    }

    public String getKey() {
        return key;
    }

    public String getCreatorEmail() {
        return creatorEmail;
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

    public List<DayModel> getDays() {
        return days;
    }

    public String toStringOpeningHours() {
        final String[] dayNames = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri"};
        ArrayList<TimeModel[]> times = new ArrayList<TimeModel[]>();
        ArrayList<ArrayList<Integer>> days = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < this.days.size(); i++) {
            if(this.days.get(i) != null) {
                TimeModel[] currTime = new TimeModel[] {this.days.get(i).getStartTime(), this.days.get(i).getEndTime()};
                boolean hasFound = false;
                int foundIndex = -1;
                for(int j = 0; j < times.size() && !hasFound; j++) {
                    if(times.get(j)[0].equals(this.days.get(i).getStartTime()) && times.get(j)[1].equals(this.days.get(i).getEndTime())) {
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

    public void addNextMonthToDatabase() {
        DaysDatabaseHelper daysDBHelper = new DaysDatabaseHelper();
        for(DayModel day : days) {
            if(day != null) {
                for(int i = 1; i <= 4; i++) {
                    Date nextDate = day.findNextDate(i);
                    String daysKey = this.key + "_" + new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(nextDate);
                    day.setEventID(this.key);
                    day.setKey(daysKey);
                    day.setAppointmentDetails(nextDate, daysKey);
                    daysDBHelper.getDay(daysKey).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            System.out.println(documentSnapshot != null);
                            if(documentSnapshot != null) {
                                daysDBHelper.addDays(daysKey, day);
                            }
                        }
                    });
                }
            }
        }
    }

    public boolean checkIsSameCreator(String creatorEmail) {
        return this.creatorEmail == creatorEmail;
    }

    public boolean checkIsToday() {
        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
        return weekday <= 4 ? this.days.get(weekday) != null : false;
    }
}
