package com.fereshte.event_reminder.util.alarm;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fereshte.event_reminder.data.local.Repository;
import com.fereshte.event_reminder.data.model.EventModel;
import com.fereshte.event_reminder.util.datetime.DateParser;
import com.fereshte.event_reminder.util.notification.NotificationHelper;
import com.fereshte.event_reminder.R;
import java.util.ArrayList;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null && intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            setAlarmsAfterBoot(context);
        }
        else setAlarmsNotification(context, intent);
    }

    private void setAlarmsAfterBoot(Context context){
        Repository repository = new Repository(context);
        DateParser dateParser = new DateParser(context);
        ArrayList<EventModel> eventModel = repository.getEventModelList();
        for(EventModel event : eventModel){
            if (event.getEventDate() > System.currentTimeMillis())
                new EventAlarmManager(context, event.getEventId(), event.getEventDate())
                        .createEventAlarmManger(event.getEventTitle(),
                                dateParser.getEventTime(event.getEventDate()));
        }
    }

    private void setAlarmsNotification(Context context, Intent intent){
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationBuilder = notificationHelper
                .getNotificationChannel(intent.getStringExtra(context.getString(R.string.TITLE)),
                        intent.getStringExtra(context.getString(R.string.TIME)),
                        R.drawable.ic_round_alarm_24);
        notificationHelper.getManager().notify((int) System.currentTimeMillis(), notificationBuilder.build());
    }

}
