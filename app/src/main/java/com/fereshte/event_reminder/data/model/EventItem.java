package com.fereshte.event_reminder.data.model;

public class EventItem {

    private int id;
    private String eventTitle;
    private String eventWeekDay;
    private String eventTime;
    private String eventOccasion;
    private int eventColor;

    public EventItem(int id, String eventTitle, String eventWeekDay,
                     String eventTime, String eventOccasion, int eventColor) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventWeekDay = eventWeekDay;
        this.eventTime = eventTime;
        this.eventOccasion = eventOccasion;
        this.eventColor = eventColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventWeekDay() {
        return eventWeekDay;
    }

    public void setEventWeekDay(String eventWeekDay) {
        this.eventWeekDay = eventWeekDay;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventOccasion() {
        return eventOccasion;
    }

    public void setEventOccasion(String eventOccasion) {
        this.eventOccasion = eventOccasion;
    }

    public int getEventColor() {
        return eventColor;
    }

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }
}

