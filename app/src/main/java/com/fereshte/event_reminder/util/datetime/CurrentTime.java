package com.fereshte.event_reminder.util.datetime;

import java.util.Calendar;
import java.util.TimeZone;
import saman.zamani.persiandate.PersianDate;

public class CurrentTime {

    public CurrentTime(){
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("GMT+4:30"));
    }

    public static int getCurrentHour(){
        PersianDate persianDate = new PersianDate();
        return persianDate.getHour();
    }

    public static int getCurrentMinute(){
        PersianDate persianDate = new PersianDate();
        return persianDate.getMinute();
    }

    public static Long getCurrentTime(){
        PersianDate persianDate = new PersianDate();
        return persianDate.getTime();
    }

}
