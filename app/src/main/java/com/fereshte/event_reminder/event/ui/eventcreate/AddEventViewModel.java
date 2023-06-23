package com.fereshte.event_reminder.event.ui.eventcreate;

import android.app.Application;
import android.util.Log;
import com.fereshte.event_reminder.data.local.Repository;
import com.fereshte.event_reminder.data.model.DateModel;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.data.model.RequestModel;
import com.fereshte.event_reminder.data.model.TimeModel;
import com.fereshte.event_reminder.util.alarm.EventAlarmManager;
import com.fereshte.event_reminder.util.datetime.CurrentDate;
import com.fereshte.event_reminder.util.datetime.CurrentTime;
import com.fereshte.event_reminder.util.datetime.DateParameters;
import com.fereshte.event_reminder.util.datetime.DateParser;
import com.fereshte.event_reminder.R;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class AddEventViewModel extends AndroidViewModel {

    private final Repository repository;
    private final DateParser dateParser;
    private final DateParameters dateParameters;

    private DateModel selectedDate;
    private TimeModel selectedTime;

    private String selectedOccasion;

    public final int MIN_TITLE_LENGTH = 3;
    private int indexColor;
    private Integer eventId;

    private final MutableLiveData<Boolean> titleValidation = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visibleDescriptionTxv = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visibleLocationTxv = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visibleLinkTxv = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visiblePlusIcon = new MutableLiveData<>();

    private final MutableLiveData<String> eventTime = new MutableLiveData<>();
    private final MutableLiveData<String> eventDate = new MutableLiveData<>();
    private final MutableLiveData<String> occasion = new MutableLiveData<>();

    private final MutableLiveData<Integer> color = new MutableLiveData<>();

    private final MutableLiveData<DateModel> dateModel = new MutableLiveData<>();
    private final MutableLiveData<TimeModel> timeModel = new MutableLiveData<>();

    private final MutableLiveData<EventModel> eventModel = new MutableLiveData<>();

    public AddEventViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        dateParser = new DateParser(application);
        dateParameters = new DateParameters(application);

        initObjects();
        setupMethods();
    }

    private void initObjects() {
        setSelectedOccasion("");
        setIndexColor(0);
        setEventId(0);
        setVisibleDescriptionTxv(false);
        setVisibleLocationTxv(false);
        setVisibleLinkTxv(false);
    }

    private void setupMethods() {
        getInitialTime();
        getInitialDate();
    }

    public String getSelectedOccasion() {
        return selectedOccasion;
    }

    public void setSelectedOccasion(String selectedOccasion) {
        this.selectedOccasion = selectedOccasion;
    }

    public LiveData<Boolean> getTitleValidation() {
        return titleValidation;
    }

    public void isTitleValid(Boolean valid) {
        titleValidation.setValue(valid);
    }

    public LiveData<String> getEventTime() {
        return eventTime;
    }

    public void setEventTime(String time) {
        eventTime.setValue(time);
    }

    public LiveData<String> getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        eventDate.setValue(date);
    }

    public LiveData<DateModel> getDateModel() {
        return dateModel;
    }

    public void setDateModel(DateModel dateModel) {
        this.dateModel.setValue(dateModel);
    }

    public LiveData<TimeModel> getTimeModel() {
        return timeModel;
    }

    public void setTimeModel(TimeModel timeModel) {
        this.timeModel.setValue(timeModel);
    }

    public LiveData<String> getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion.setValue(occasion);
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

    public int getIndexColor() {
        return indexColor;
    }

    public void setIndexColor(int indexColor) {
        this.indexColor = indexColor;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LiveData<Boolean> getVisibleDescriptionTxv() {
        return visibleDescriptionTxv;
    }

    public void setVisibleDescriptionTxv(Boolean isVisible) {
        this.visibleDescriptionTxv.setValue(isVisible);
    }

    public LiveData<Boolean> getVisibleLocationTxv() {
        return visibleLocationTxv;
    }

    public void setVisibleLocationTxv(Boolean isVisible) {
        this.visibleLocationTxv.setValue(isVisible);
    }

    public LiveData<Boolean> getVisibleLinkTxv() {
        return visibleLinkTxv;
    }

    public void setVisibleLinkTxv(Boolean isVisible) {
        this.visibleLinkTxv.setValue(isVisible);
    }

    public LiveData<Boolean> getVisiblePlusIcon() {
        return visiblePlusIcon;
    }

    public void setVisiblePlusIcon(Boolean isVisible) {
        this.visiblePlusIcon.setValue(isVisible);
    }


    private void getInitialTime() {
        setEventTime(DateParser.addZeroToHourAndMinute(CurrentTime.getCurrentHour(), CurrentTime.getCurrentMinute()));
        selectedTime = new TimeModel(CurrentTime.getCurrentHour(), CurrentTime.getCurrentMinute());
    }

    private void getInitialDate() {
        setEventDate(String.format(getApplication().getApplicationContext().getString(R.string.dateFormat), CurrentDate.getCurrentDay(),
                CurrentDate.getCurrentMonthName(),
                CurrentDate.getCurrentYear()));
        selectedDate = new DateModel(CurrentDate.getCurrentYear(),
                CurrentDate.getCurrentMonth(),
                CurrentDate.getCurrentDay());
    }

    public void onClickDate() {
        setDateModel(selectedDate);
    }

    public void setDateDialog(int year, int month, int day) {
        selectedDate = new DateModel(year, month + AddEventFragment.MONTH_DIFFER, day);
        setTimeModel(selectedTime);
    }

    public void setTimeDialog(int hour, int minute) {
        setEventTime(DateParser.addZeroToHourAndMinute(hour, minute));

        setEventDate(String.format(getApplication().getResources().getString(R.string.dateFormat), selectedDate.getDay(),
                CurrentDate.getCurrentMonthName(selectedDate.getMonth()),
                selectedDate.getYear()));

        selectedTime = new TimeModel(hour, minute);
    }

    private Long createTimestamp() {
        PersianDate persianDate = new PersianDate();
        persianDate.setShYear(selectedDate.getYear())
                .setShMonth(selectedDate.getMonth())
                .setShDay(selectedDate.getDay())
                .setHour(selectedTime.getHour())
                .setMinute(selectedTime.getMinute());
        return persianDate.getTime();
    }

    public void onClickOccasion() {
        setOccasion(getSelectedOccasion());
    }

    public void getOccasionItem(String occasion) {
        setSelectedOccasion(occasion);
    }

    public void setEventColor(int color){
        setColor(color);
        setIndexColor(color);
    }

    public void storeEventInDataBase(EventModel eventModel) {
        if (eventModel.getEventTitle().length() < MIN_TITLE_LENGTH) {
            isTitleValid(false);
        } else {
            eventModel.setEventDate(createTimestamp());
            eventModel.setEventOccasion(getSelectedOccasion());
            eventModel.setEventColor(getIndexColor());
            if (getEventId() > 0){
                repository.updateEventInDb(eventModel, getEventId());
                repository.updateEditColumn(eventId, getApplication().getString(R.string.Edited));
            }
            else
                setEventId(repository.insertEventToDb(eventModel).intValue());
            setEventAlarm(getEventId(), eventModel);
            isTitleValid(true);
        }
    }

    private void setEventAlarm(int eventId, EventModel eventModel) {
        if (eventModel.getEventDate() > System.currentTimeMillis())
            new EventAlarmManager(getApplication(), eventId, eventModel.getEventDate())
                    .createEventAlarmManger(eventModel.getEventTitle(),
                            dateParser.getEventTime(eventModel.getEventDate()));
    }

    public void OnClickDescription() {
        setVisibleDescriptionTxv(true);
    }

    public void OnClickLocation() {
        setVisibleLocationTxv(true);
    }

    public void OnClickLink() {
        setVisibleLinkTxv(true);
    }

    public void getPlusIconVisibility() {
        if (visibleDescriptionTxv.getValue() != null && visibleLocationTxv.getValue() != null && visibleLinkTxv.getValue() != null)
            setVisiblePlusIcon(!visibleDescriptionTxv.getValue() || !visibleLocationTxv.getValue() || !visibleLinkTxv.getValue());
    }


    public void getEventDetailFromDb(int eventId) {
        setEventId(eventId);
        EventModel eventModel = repository.getEventDetailById(eventId);
        setEventModel(eventModel);
        setSelectedOccasion(eventModel.getEventOccasion());
        setIndexColor(eventModel.getEventColor());
        setEventDetailDate(eventModel.getEventDate());
        setVisibilityOfDetail(eventModel);
    }

    private void setEventDetailDate(Long date) {
        setEventTime(dateParser.getEventTime(date));
        setEventDate(dateParser.getEventDate(date));
        selectedDate = new DateModel(dateParameters.getEventDateYear(date), dateParameters.getEventDateMonth(date),
                dateParameters.getEventDateDay(date));
        selectedTime = new TimeModel(dateParameters.getEventDateHour(date), dateParameters.getEventDateMinute(date));

    }

    private void setVisibilityOfDetail(EventModel eventModel) {
        setVisibleDescriptionTxv(!eventModel.getEventNote().equals(""));
        setVisibleLocationTxv(!eventModel.getEventLocation().equals(""));
        setVisibleLinkTxv(!eventModel.getEventLink().equals(""));
        getPlusIconVisibility();
    }


    public void getEventFromServer(String token) {
        Call<RequestModel> call = repository.getEvent(token);
        call.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(@NonNull Call<RequestModel> call, @NonNull Response<RequestModel> response) {
                if(response.body() != null){
                    EventModel eventModel = mapEventModel(response.body());
                    Log.e("occasion -> ", eventModel.getEventOccasion());
                    setEventModel(eventModel);
                    setSelectedOccasion(eventModel.getEventOccasion());
                    setIndexColor(eventModel.getEventColor());
                    setEventDetailDate(eventModel.getEventDate());
                    setVisibilityOfDetail(eventModel);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestModel> call, @NonNull Throwable t) {
                Log.e("onFailure->", t.getMessage());
            }
        });
    }

    private String getOccasionText(Integer occasionNum) {
        String[] occasionArray = getApplication().getResources().getStringArray(R.array.occasion);
        String occasion = "";
        for (int i = 0; i < occasionArray.length; i++) {
            if ((i + 1) == occasionNum) {
                occasion = occasionArray[i];
                break;
            }
        }
        return occasion;
    }

    private EventModel mapEventModel(RequestModel requestModel){
        String title = requestModel.getTitle();
        Long time = Long.parseLong(requestModel.getStartTime());
        int occasion = requestModel.getOccasion();
        int color = requestModel.getColor();
        String description = requestModel.getDescription();
        String location = requestModel.getLocation();
        String link = requestModel.getLink();

        return new EventModel(title, time, getOccasionText(occasion), description, location, link, color);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.eventTable.dataBaseHelper.close();
    }

}