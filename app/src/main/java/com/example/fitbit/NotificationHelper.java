package com.example.fitbit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationHelper {
    public static final String CHANNEL_ID = "scheduled_notifications";

    public static void createNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Scheduled Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for scheduled notifications");
            notificationManager.createNotificationChannel(channel);
        }
    }
}
