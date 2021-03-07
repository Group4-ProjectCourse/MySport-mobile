package com.mysport.mysport_mobile.models;

import com.mysport.mysport_mobile.utils.ObjectUtils;

public class SportEvent {
    private String name;
    private String description;
    private CalendarRange calendarRange;

    public SportEvent(String name, String description, CalendarRange calendarRange) {
        this.name = name;
        this.description = description;
        this.calendarRange = calendarRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return ObjectUtils.equals(name, sportEvent.name) &&
                ObjectUtils.equals(description, sportEvent.description) &&
                ObjectUtils.equals(calendarRange, sportEvent.calendarRange);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hash(name, description, calendarRange);
    }
}
