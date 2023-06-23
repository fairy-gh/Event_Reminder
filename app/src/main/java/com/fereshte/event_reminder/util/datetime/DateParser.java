package com.fereshte.event_reminder.util.datetime;

import android.annotation.SuppressLint;
import android.content.Context;

import com.fereshte.event_reminder.R;

import saman.zamani.persiandate.PersianDate;

public class DateParser {

    private final Context context;
    private PersianDate persianDate;

    public DateParser(Context context) {
        this.context = context;
    }


    public String getEventWeekDay(Long eventTimestamp) {
        persianDate = new PersianDate(eventTimestamp);
        return persianDate.dayName();
    }

    public String getEventTime(Long eventTimeStamp) {
        persianDate = new PersianDate(eventTimeStamp);
        return addZeroToHourAndMinute(persianDate.getHour(), persianDate.getMinute());
    }

    public String getEventDate(Long eventTimestamp){
        persianDate = new PersianDate(eventTimestamp);
        return String.format(context.getResources().getString(R.string.dateFormat), persianDate.getShDay(),
                persianDate.monthName(),
                persianDate.getShYear());
    }

    @SuppressLint("DefaultLocale")
    public static String addZeroToHourAndMinute(int hour, int minute){
        String time = "";
        //simplify it later
        if(hour <= 9 && minute <= 9)
            time = String.format("%s:%s" , "0" + hour, "0" + minute);
        else if (hour <= 9 && minute > 9)
            time = String.format("%s:%d" , "0" + hour, minute);
        else if (hour > 9 && minute <= 9)
            time = String.format("%d:%s" , hour, "0" + minute);
        else if (hour > 9 && minute > 9)
            time = String.format("%d:%d" , hour, minute);
        return time;
    }

}
