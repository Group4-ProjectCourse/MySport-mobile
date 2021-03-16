package com.mysport.mysport_mobile.models;

import com.mysport.mysport_mobile.utils.ObjectUtils;

import java.util.ArrayList;

public class SportEvent {
    private String sportName;
    private ArrayList<Member> participants;
    private CalendarRange calendarRange;

    public SportEvent(String sportName, ArrayList<Member> participants, CalendarRange calendarRange) {
        this.sportName = sportName;
        this.participants = participants;
        this.calendarRange = calendarRange;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public ArrayList<Member> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Member> participants) {
        this.participants = participants;
    }

    public CalendarRange getCalendarRange() {
        return calendarRange;
    }

    public void setCalendarRange(CalendarRange calendarRange) {
        this.calendarRange = calendarRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SportEvent sportEvent = (SportEvent) o;
        return ObjectUtils.equals(sportName, sportEvent.sportName) &&
                ObjectUtils.equals(participants, sportEvent.participants) &&
                ObjectUtils.equals(calendarRange, sportEvent.calendarRange);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(sportName, participants, calendarRange);
    }

    public String getDescription() {
        return getParticipants().toString();
    }

    public String[] getNames() {
        String[] names = new String[participants.size()];
        for(int i = 0; i < names.length; i++)
            names[i] = participants.get(i).toString();

        return names;
    }
}
