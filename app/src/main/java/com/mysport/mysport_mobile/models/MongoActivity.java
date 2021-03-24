package com.mysport.mysport_mobile.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MongoActivity {
    private final String title;
    private final String location;
    private final Calendar startDate;
    private final Calendar endDate;
    private ArrayList<String> participants;

    public MongoActivity(String title, Calendar startDate, Calendar endDate, String location, ArrayList<String> participants) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public String getLocation() {
        return location;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public int getStartHour(){
        return startDate.get(Calendar.HOUR_OF_DAY);
    }

    public int getEndHour(){
        return endDate.get(Calendar.HOUR_OF_DAY);
    }

    public int getStartMinutes(){
        return startDate.get(Calendar.MINUTE);
    }

    public int getEndMinutes(){
        return endDate.get(Calendar.MINUTE);
    }
}