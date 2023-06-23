package com.fereshte.event_reminder.data.local;

import android.content.Context;
import com.fereshte.event_reminder.base.network.RetrofitClient;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.data.model.RequestModel;
import com.fereshte.event_reminder.data.model.ResponseModel;
import com.fereshte.event_reminder.data.model.SharedEventModel;
import com.fereshte.event_reminder.data.remote.ApiInterface;
import com.fereshte.event_reminder.util.Constants;
import java.util.ArrayList;
import retrofit2.Call;

public class Repository {

    private Context context;
    public EventTable eventTable;
    public SharedEventTable sharedEventTable;

    public Repository(Context context) {
        this.context = context;
        eventTable = new EventTable(context);
        sharedEventTable = new SharedEventTable(context);
    }

    public ArrayList<EventModel> getEventModelList() {
        return eventTable.fetchEventsFromDb();
    }

    public Long insertEventToDb(EventModel eventModel) {
        return eventTable.insertNewEventToDb(eventModel);
    }

    public EventModel getEventDetailById(int eventId) {
        return eventTable.fetchEventById(eventId);
    }

    public void deleteEventFromDB(int eventId) {
        eventTable.deleteEventFromDb(eventId);
    }

    public void updateEventInDb(EventModel eventModel, int eventId){
        eventTable.updateEvent(eventModel, eventId);
    }

    public void insertShareToDb(SharedEventModel sharedEventModel){
        sharedEventTable.insertSharedEvent(sharedEventModel);
    }

    public boolean existEventInDb(int eventId){
        return sharedEventTable.existEventInDb(eventId);
    }

    public boolean isEventEdited(int eventId){
        return sharedEventTable.isEventEdited(eventId);
    }

    public String fetchEventLink(int eventId){
        return sharedEventTable.fetchSharedEventsFromDb(eventId).getResponseModel().getLink();
    }

    public void updateEditColumn(int eventId, String edit){
        sharedEventTable.updateEditedColumn(eventId, edit);
    }

    public void updateSharedEvent(int id, SharedEventModel sharedEventModel){
        sharedEventTable.updateSharedEvent(id, sharedEventModel);
    }

    public Call<ResponseModel> shareEvent(RequestModel requestModel){
        return RetrofitClient.getInstance(Constants.BASE_EVENT_URL, context).create(ApiInterface.class).shareEvent(requestModel);
    }

    public Call<RequestModel> getEvent(String token){
        return RetrofitClient.getInstance(Constants.BASE_EVENT_URL, context).create(ApiInterface.class).getEvent(token);
    }

}
