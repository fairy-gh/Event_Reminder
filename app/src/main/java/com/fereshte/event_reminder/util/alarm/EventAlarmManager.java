package com.fereshte.event_reminder.util.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.fereshte.event_reminder.R;

public class EventAlarmManager {

    private final Context context;

    private final int eventId;
    private final Long timestamp;

    public EventAlarmManager(Context context, int eventId, Long timestamp) {
        this.context = context;
        this.eventId = eventId;
        this.timestamp = timestamp;
    }

    private PendingIntent setPendingIntent(Intent alarmIntent) {
        return PendingIntent.getBroadcast(context, eventId, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void setAlarm(PendingIntent pendingIntent) {
        AlarmManager eventAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            eventAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            eventAlarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        else eventAlarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }

    public void createEventAlarmManger(String eventTitle, String eventTime) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra(context.getString(R.string.TITLE), eventTitle);
        alarmIntent.putExtra(context.getString(R.string.TIME), eventTime);
        setAlarm(setPendingIntent(alarmIntent));
    }
}
