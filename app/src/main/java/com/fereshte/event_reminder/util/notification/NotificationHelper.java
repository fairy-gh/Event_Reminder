package com.fereshte.event_reminder.util.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import com.fereshte.event_reminder.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public NotificationCompat.Builder getNotificationChannel(String title, String text, int smallIcon) {
        setNotificationChannel();
        return new NotificationCompat.Builder(context,
                context.getString(R.string.EventNotificationChannelId))
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(text);
    }

    public NotificationManagerCompat getManager() {
        return NotificationManagerCompat.from(context);
    }

    private void setNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(context.getString(R.string.EventNotificationChannelId),
                            context.getString(R.string.EventNotificationName),
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(context.getString(R.string.notifyAlarm));
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
