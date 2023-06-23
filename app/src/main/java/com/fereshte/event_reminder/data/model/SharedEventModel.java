package com.fereshte.event_reminder.data.model;

public class SharedEventModel {

    private int id;
    private int eventId;
    ResponseModel responseModel;
    private String editedEvent;

    public SharedEventModel() {
    }

    public SharedEventModel(int eventId, ResponseModel responseModel, String editedEvent) {
        this.eventId = eventId;
        this.responseModel = responseModel;
        this.editedEvent = editedEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public String getEditedEvent() {
        return editedEvent;
    }

    public void setEditedEvent(String editedEvent) {
        this.editedEvent = editedEvent;
    }
}
