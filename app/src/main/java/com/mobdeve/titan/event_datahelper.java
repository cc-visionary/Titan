package com.mobdeve.titan;

import java.util.ArrayList;

public class event_datahelper {

    public ArrayList<event> initEvents()
    {
        String[] eventNames= {"Massage", "Study Room", "Consultation", "Haircut"};
        String[] startTimes= {"7:00", "9:00" ,"11:00", "13:00" };
        String[] endTimes= {"12:00", "12:00" ,"15:00", "19:00" };


        ArrayList<event> data = new ArrayList<>();

        data.add(new event(eventNames[0],startTimes[0],endTimes[0], 5, 60));
        data.add(new event(eventNames[1],startTimes[1],endTimes[1], 3, 60));
        data.add(new event(eventNames[2],startTimes[2],endTimes[2], 12, 30));
        data.add(new event(eventNames[3],startTimes[3],endTimes[3], 14, 30));


        return data;


    }
}
