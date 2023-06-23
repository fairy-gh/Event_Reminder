package com.fereshte.event_reminder.data.model;

public class EventModel {

    private int eventId;
    private String eventTitle;
    private Long eventDate;
    private String eventOccasion;
    private String eventNote;
    private String eventLocation;
    private String eventLink;
    private int eventColor;

    public EventModel(){}

    public EventModel(String eventTitle,
                      Long eventDate, String eventOccasion,
                      String eventNote, String eventLocation, String eventLink, int eventColor) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventOccasion = eventOccasion;
        this.eventNote = eventNote;
        this.eventLocation = eventLocation;
        this.eventLink = eventLink;
        this.eventColor = eventColor;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventOccasion() {
        return eventOccasion;
    }

    public void setEventOccasion(String eventOccasion) {
        this.eventOccasion = eventOccasion;
    }

    public String getEventNote() {
        return eventNote;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public int getEventColor() {
        return eventColor;
    }

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }
}
