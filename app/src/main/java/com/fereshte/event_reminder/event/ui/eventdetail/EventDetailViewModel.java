package com.fereshte.event_reminder.event.ui.eventdetail;

import android.app.Application;
import android.util.Log;
import com.fereshte.event_reminder.data.local.Repository;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.data.model.RequestModel;
import com.fereshte.event_reminder.data.model.ResponseModel;
import com.fereshte.event_reminder.data.model.SharedEventModel;
import com.fereshte.event_reminder.util.alarm.CancelAlarms;
import com.fereshte.event_reminder.util.datetime.DateParser;
import com.fereshte.event_reminder.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailViewModel extends AndroidViewModel {

    private final Repository repository;
    private final DateParser dateParser;
    private final CancelAlarms cancelAlarms;

    private String title;

    private final MutableLiveData<EventModel> eventModel = new MutableLiveData<>();

    private final MutableLiveData<Integer> color = new MediatorLiveData<>();
    private final MutableLiveData<String> eventDate = new MutableLiveData<>();
    private final MutableLiveData<String> eventTime = new MutableLiveData<>();
    private final MutableLiveData<List<String>> linkAndTitle = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> isEventDeleted = new MediatorLiveData<>();

    public EventDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        dateParser = new DateParser(application);
        cancelAlarms = new CancelAlarms(application);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LiveData<Integer> getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color.setValue(color);
    }

    public LiveData<EventModel> getEventModel() {
        return eventModel;
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel.setValue(eventModel);
    }

    public LiveData<String> getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        eventDate.setValue(date);
    }

    public LiveData<String> getEventTime() {
        return eventTime;
    }

    public void setEventTime(String time) {
        eventTime.setValue(time);
    }

    public LiveData<Boolean> getIsEventDeleted() {
        return isEventDeleted;
    }

    public void setIsEventDeleted(Boolean isEventDeleted) {
        this.isEventDeleted.setValue(isEventDeleted);
    }

    public void getEventDetailFromDb(int eventId) {
        EventModel eventModel = repository.getEventDetailById(eventId);
        setEventModel(eventModel);
        setShapeColor(eventModel.getEventColor());
        setEventDate(dateParser.getEventDate(eventModel.getEventDate()));
        setEventTime(dateParser.getEventTime(eventModel.getEventDate()));
    }

    private void setShapeColor(int colorIndex) {
        int[] colorPalette = getApplication().getResources().getIntArray(R.array.colorPalette);
        setColor(colorPalette[colorIndex]);
    }

    public LiveData<List<String>> getLinkAndTitle() {
        return linkAndTitle;
    }

    public void setLinkAndTitle(List<String> linkAndTitle) {
        this.linkAndTitle.setValue(linkAndTitle);
    }


    public void deleteEventFromDb(int eventId) {
        repository.deleteEventFromDB(eventId);
        cancelAlarms.cancelEventAlarmManger(eventId);
        setIsEventDeleted(true);
    }

    public void shareEvent(int eventId) {

        if (!repository.existEventInDb(eventId)) {
            getResponseAndInsert(eventId);
        } else if (repository.existEventInDb(eventId) && !repository.isEventEdited(eventId)) {
            String link = repository.fetchEventLink(eventId);
            addLinkAndTitle(link, getRequestModel(eventId).getTitle());
        } else if (repository.existEventInDb(eventId) && repository.isEventEdited(eventId)) {
            getResponseAndUpdate(eventId);
        }

    }

    private Integer getOccasionNumber(EventModel eventModel) {
        String[] occasionArray = getApplication().getResources().getStringArray(R.array.occasion);
        Integer occasion = null;
        for (int i = 0; i < occasionArray.length; i++) {
            if (eventModel.getEventOccasion().equals(occasionArray[i])) {
                occasion = i + 1;
                break;
            }
            else occasion = -1;
        }
        return occasion;
    }

    private RequestModel getRequestModel(int eventId) {
        EventModel eventModel = repository.getEventDetailById(eventId);
        setTitle(eventModel.getEventTitle());
        boolean[] remind = {true, false, false, false, false, false, true};
        int color = eventModel.getEventColor();
        String startTime = String.valueOf(eventModel.getEventDate());
        String description = eventModel.getEventNote();
        String location = eventModel.getEventLocation();
        String link = eventModel.getEventLink();

        return new RequestModel("", getTitle(), false, remind, "", getOccasionNumber(eventModel), color,
                startTime, startTime, "", description, link, location);
    }

    private void getResponseAndInsert(int eventId) {
        Call<ResponseModel> call = repository.shareEvent(getRequestModel(eventId));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    repository.insertShareToDb(new SharedEventModel(eventId, response.body(), ""));
                    addLinkAndTitle(response.body().getLink(), getRequestModel(eventId).getTitle());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Log.e("onFailure->", t.getMessage());
            }
        });
    }

    private void getResponseAndUpdate(int eventId) {
        Call<ResponseModel> call = repository.shareEvent(getRequestModel(eventId));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    SharedEventModel sharedEventModel = new SharedEventModel(eventId, response.body(), getApplication().getString(R.string.Edited));
                    repository.updateSharedEvent(eventId, sharedEventModel);
                    addLinkAndTitle(response.body().getLink(), getRequestModel(eventId).getTitle());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Log.e("onFailure->", t.getMessage());
            }
        });
    }


    private void addLinkAndTitle(String link, String title) {
        ArrayList<String> linkAndTitle = new ArrayList<>();
        linkAndTitle.add(link);
        linkAndTitle.add(title);
        setLinkAndTitle(linkAndTitle);
    }

}

