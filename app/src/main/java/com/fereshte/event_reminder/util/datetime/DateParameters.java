package com.fereshte.event_reminder.util.datetime;

import android.content.Context;

import com.fereshte.event_reminder.R;

import saman.zamani.persiandate.PersianDate;

public class DateParameters {

    private final Context context;
    private PersianDate persianDate;

    public DateParameters(Context context) {
        this.context = context;
    }

    public String getEventDate(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return String.format(context.getResources().getString(R.string.dateFormat), persianDate.getShDay(),
                persianDate.monthName(),
                persianDate.getShYear());
    }

    public int getEventDateYear(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.getShYear();
    }

    public int getEventDateMonth(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.getShMonth();
    }

    public int getEventDateDay(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.getShDay();
    }

    public int getEventDateHour(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.getHour();
    }

    public int getEventDateMinute(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.getMinute();
    }
}
