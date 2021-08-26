package com.mobdeve.titan;

import java.util.ArrayList;

public class EventDataHelper {
    public static ArrayList<EventModel> loadEventData() {
        ArrayList<EventModel> data = new ArrayList<EventModel>();

        data.add(new EventModel(1, 1, "Massage", "Binondo, Manila", "09123456789", new DayModel[] {
                new DayModel(new TimeModel(8, 0), new TimeModel(16, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(16, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(16, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(16, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(16, 0), 15),
                null,
                null,
        }));

        data.add(new EventModel(2, 2, "Pet Checkup", "Malabon, Manila", "0923123231", new DayModel[] {
                new DayModel(new TimeModel(8, 0), new TimeModel(12, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(12, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(14, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(14, 0), 15),
                new DayModel(new TimeModel(8, 0), new TimeModel(14, 0), 15),
                null,
                null,
        }));

        data.add(new EventModel(3, 2, "Review Class", "Santa Cruz, Manila", "0923123231", new DayModel[] {
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 15),
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 15),
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 15),
                null,
                null,
        }));

        data.add(new EventModel(4, 2, "Yoga Session", "Quezon, Manila", "0923123231", new DayModel[] {
                new DayModel(new TimeModel(8, 0), new TimeModel(12, 0), 15),
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(13, 0), 15),
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(14, 0), 15),
                null,
                null,
        }));

        data.add(new EventModel(5, 1, "Boxing Training", "Dasmarinas, Manila", "0923123231", new DayModel[] {
                null,
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 60),
                null,
                null,
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 60),
                new DayModel(new TimeModel(8, 0), new TimeModel(20, 0), 60),
        }));

        return data;
    }
}
