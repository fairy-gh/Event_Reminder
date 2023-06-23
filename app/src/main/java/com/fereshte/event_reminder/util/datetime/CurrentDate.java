package com.fereshte.event_reminder.util.datetime;

import java.util.Calendar;
import java.util.TimeZone;
import saman.zamani.persiandate.PersianDate;

public class CurrentDate {

    public CurrentDate() {
        Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("GMT+4:30"));
    }

    public static int getCurrentYear() {
        PersianDate persianDate = new PersianDate();
        return persianDate.getShYear();
    }

    public static int getCurrentMonth() {
        PersianDate persianDate = new PersianDate();
        return persianDate.getShMonth();
    }

    public static String getCurrentMonthName() {
        PersianDate persianDate = new PersianDate();
        return persianDate.monthName();
    }

    public static String getCurrentMonthName(int month) {
        PersianDate persianDate = new PersianDate();
        return persianDate.monthName(month);
    }

    public static int getCurrentDay() {
        PersianDate persianDate = new PersianDate();
        return persianDate.getShDay();
    }

}
