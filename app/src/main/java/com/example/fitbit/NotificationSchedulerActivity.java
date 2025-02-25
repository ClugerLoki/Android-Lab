package com.example.fitbit;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class NotificationSchedulerActivity extends AppCompatActivity {

    EditText etMessage;
    Button btnSchedule, btnPopNow;
    TimePicker timePicker;
    CheckBox cbRepeatDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_scheduler);

        etMessage = findViewById(R.id.etMessage);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnPopNow = findViewById(R.id.btnPopNow);
        timePicker = findViewById(R.id.timePicker);
        cbRepeatDaily = findViewById(R.id.cbRepeatDaily);

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                if (message.isEmpty()) {
                    etMessage.setError("Enter a message");
                    return;
                }

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                scheduleNotification(NotificationSchedulerActivity.this, message, calendar, cbRepeatDaily.isChecked());
            }
        });

        btnPopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                if (message.isEmpty()) {
                    etMessage.setError("Enter a message");
                    return;
                }
                showInstantNotification(message);
            }
        });
    }

    private void scheduleNotification(Context context, String message, Calendar calendar, boolean repeatDaily) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("notification_message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            if (repeatDaily) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
            Toast.makeText(context, "Notification Scheduled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showInstantNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("instant_notifications", "Instant Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder builder = new Notification.Builder(this, "instant_notifications")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Instant Notification")
                .setContentText(message)
                .setAutoCancel(true);

        notificationManager.notify(2, builder.build());
    }
}
