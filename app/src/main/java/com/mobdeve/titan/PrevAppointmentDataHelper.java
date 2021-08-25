package com.mobdeve.titan;

import java.util.ArrayList;
import java.util.Collections;

public class PrevAppointmentDataHelper {
    public ArrayList<Appointments> InitializeData() {
        String[] usernames = {"Jethro", "Patrick", "Christopher"};
        ArrayList<Appointments> data = new ArrayList<>();
        data.add(new Appointments(
                usernames[0],
                "03/13/2001",
                "1:00PM-3:00PM",
                 R.drawable.success));
        data.add(new Appointments(
                usernames[1],
                "05/15/2015",
                "2:00PM-5:00PM",
                R.drawable.success));
        data.add(new Appointments(
                usernames[0],
                "08/31/2021",
                "9:00PM-10:00PM",
                R.drawable.success));
        data.add(new Appointments(
                usernames[2],
                "08/24/2021",
                "9:00AM-6:00PM",
                R.drawable.error));
        Collections.shuffle(data);

        return data;
    }
}
