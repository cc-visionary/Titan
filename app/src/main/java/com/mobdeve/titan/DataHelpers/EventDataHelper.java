package com.mobdeve.titan.DataHelpers;

import com.mobdeve.titan.DatabaseHelpers.EventDatabaseHelper;
import com.mobdeve.titan.Models.DayModel;
import com.mobdeve.titan.Models.EventModel;
import com.mobdeve.titan.Models.TimeModel;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventDataHelper {
    public static void generateEventDatabase() {
        ArrayList<EventModel> data = new ArrayList<EventModel>();

        ArrayList<DayModel> daysOne = new ArrayList<>();
        daysOne.add(new DayModel("Massage", new TimeModel(8, 0), new TimeModel(16, 0), 15, Calendar.MONDAY));
        daysOne.add(new DayModel("Massage", new TimeModel(8, 0), new TimeModel(16, 0), 15, Calendar.TUESDAY));
        daysOne.add(new DayModel("Massage", new TimeModel(8, 0), new TimeModel(16, 0), 15, Calendar.WEDNESDAY));
        daysOne.add(new DayModel("Massage", new TimeModel(8, 0), new TimeModel(16, 0), 15, Calendar.THURSDAY));
        daysOne.add(new DayModel("Massage", new TimeModel(8, 0), new TimeModel(16, 0), 15, Calendar.FRIDAY));

        EventModel event1 = new EventModel("host1@gmail.com", "Massage", "Binondo, Manila", "09123456789", daysOne);

        ArrayList<DayModel> daysTwo = new ArrayList<>();
        daysTwo.add(new DayModel("Pet Checkup", new TimeModel(8, 0), new TimeModel(12, 0), 15, Calendar.MONDAY));
        daysTwo.add(new DayModel("Pet Checkup", new TimeModel(8, 0), new TimeModel(12, 0), 15, Calendar.TUESDAY));
        daysTwo.add(new DayModel("Pet Checkup", new TimeModel(8, 0), new TimeModel(14, 0), 15, Calendar.WEDNESDAY));
        daysTwo.add(new DayModel("Pet Checkup", new TimeModel(8, 0), new TimeModel(14, 0), 15, Calendar.THURSDAY));
        daysTwo.add(new DayModel("Pet Checkup", new TimeModel(8, 0), new TimeModel(14, 0), 15, Calendar.FRIDAY));
        EventModel event2 = new EventModel("host1@gmail.com", "Pet Checkup", "Malabon, Manila", "0923123231", daysTwo);

        ArrayList<DayModel> daysThree = new ArrayList<>();
        daysThree.add(new DayModel("Review Class", new TimeModel(8, 0), new TimeModel(20, 0), 15, Calendar.MONDAY));
        daysThree.add(null);
        daysThree.add(new DayModel("Review Class", new TimeModel(8, 0), new TimeModel(20, 0), 15, Calendar.WEDNESDAY));
        daysThree.add(null);
        daysThree.add(new DayModel("Review Class", new TimeModel(8, 0), new TimeModel(20, 0), 15, Calendar.FRIDAY));
        EventModel event3 = new EventModel("host1@gmail.com", "Review Class", "Santa Cruz, Manila", "0923123231", daysThree);

        ArrayList<DayModel> daysFour = new ArrayList<>();
        daysFour.add(new DayModel("Yoga Session", new TimeModel(8, 0), new TimeModel(12, 0), 15, Calendar.MONDAY));
        daysFour.add(null);
        daysFour.add(new DayModel("Yoga Session", new TimeModel(8, 0), new TimeModel(13, 0), 15, Calendar.WEDNESDAY));
        daysFour.add(null);
        daysFour.add(new DayModel("Yoga Session", new TimeModel(8, 0), new TimeModel(14, 0), 15, Calendar.FRIDAY));
        EventModel event4 = new EventModel("host1@gmail.com", "Yoga Session", "Quezon, Manila", "0923123231", daysFour);

        ArrayList<DayModel> daysFive = new ArrayList<>();
        daysFive.add(null);
        daysFive.add(null);
        daysFive.add(new DayModel("Boxing Training", new TimeModel(8, 0), new TimeModel(20, 0), 60, Calendar.WEDNESDAY));
        daysFive.add(null);
        daysFive.add(null);
        EventModel event5 = new EventModel("host1@gmail.com", "Boxing Training", "Dasmarinas, Manila", "0923123231", daysFive);

        EventDatabaseHelper eventDBHelper = new EventDatabaseHelper();
        eventDBHelper.addEvent(event1);
        event1.addNextMonthToDatabase();
        eventDBHelper.addEvent(event2);
        event2.addNextMonthToDatabase();
        eventDBHelper.addEvent(event3);
        event3.addNextMonthToDatabase();
        eventDBHelper.addEvent(event4);
        event4.addNextMonthToDatabase();
        eventDBHelper.addEvent(event5);
        event5.addNextMonthToDatabase();
    }
}
