package com.fereshte.event_reminder.event.ui.main;

import android.app.Application;
import com.fereshte.event_reminder.data.local.Repository;
import com.fereshte.event_reminder.data.model.EventItem;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.event.ui.eventcreate.AddEventFragment;
import com.fereshte.event_reminder.util.datetime.DateParser;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<List<EventItem>> eventItemList = new MutableLiveData<>();
    private final MutableLiveData<Fragment> fragment = new MutableLiveData<>();
    private final Repository repository;
    private final DateParser dateParser;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        dateParser = new DateParser(application);
    }


    public LiveData<List<EventItem>> getEventItemList() {
        return eventItemList;
    }

    public void setEventItemList(List<EventItem> eventItemList) {
        this.eventItemList.setValue(eventItemList);
    }

    public LiveData<Fragment> getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment.setValue(fragment);
    }


    public void getDataFromDb() {
        setEventItemList(prepareEventList());
    }

    private ArrayList<EventItem> prepareEventList() {
        ArrayList<EventModel> eventModelList = repository.getEventModelList();
        ArrayList<EventItem> eventListItems = new ArrayList<>();
        for (int i = 0; i < eventModelList.size(); i++) {
            int id = eventModelList.get(i).getEventId();
            String title = eventModelList.get(i).getEventTitle();
            String weekDay = dateParser.getEventWeekDay(eventModelList.get(i).getEventDate());
            String time = dateParser.getEventTime(eventModelList.get(i).getEventDate());
            String occasion = eventModelList.get(i).getEventOccasion();
            int color = eventModelList.get(i).getEventColor();
            eventListItems.add(new EventItem(id, title, weekDay, time, occasion, color));
        }
        return eventListItems;
    }

    public void goToEventCreatorFragment() {
        setFragment(AddEventFragment.getAddEventInstance(null, ""));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.eventTable.dataBaseHelper.close();
    }

}
