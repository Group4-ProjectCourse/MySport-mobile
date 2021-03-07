package com.mysport.mysport_mobile.fragments.calendar;

public class SportsModel {
    private String sportName, time, participants;
    private int view;

    public SportsModel(){

    }

    public SportsModel(String sportName, String time, String participants, int view) {
        this.sportName = sportName;
        this.time = time;
        this.participants = participants;
        this.view = view;
    }


    public String getSportName() {
        return sportName;
    }

    public SportsModel setSportName(String sportName) {
        this.sportName = sportName;

        return this;
    }

    public String getTime() {
        return time;
    }

    public SportsModel setTime(String time) {
        this.time = time;

        return this;
    }

    public String getParticipants() {
        return participants;
    }

    public SportsModel setParticipants(String participants) {
        this.participants = participants;

        return this;
    }


    public int getView() {
        return view;
    }

    public SportsModel setView(int view) {
        this.view = view;

        return this;
    }
}
