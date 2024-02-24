package com.example.account.notifications;

import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.example.account.R;
import com.example.account.alerts.AlertsActivity;


public class NotificationHelper extends NotificationCompat {
    private final Context notificationContext;
    private static final String NOTIFICATION_CHANNEL_NAME = "Alert Reminders";
    private static final String NOTIFICATION_CHANNEL_ID = "11111";


    // Set notification helper argument
    public NotificationHelper(Context context) {
        notificationContext = context;
    }

    // Create the notification
    public void createNotification() {
        Intent intent = new Intent(notificationContext, NotificationReceiver.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Wait for an event
        PendingIntent resultPendingIntent = PendingIntent.getActivity(notificationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Instantiate the notification manager
        NotificationManager notificationManager = (NotificationManager) notificationContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(notificationContext, AlertsActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(notificationContext, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Set the content of the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(notificationContext, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent)
                .setContentTitle(NOTIFICATION_CHANNEL_NAME)
                .setContentText("Don't forget to review your account alerts!")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        // Set the importance level
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        // Set the notification channel behaviour
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(androidx.appcompat.R.attr.colorPrimary);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{0, 200, 200, 500});  // Short double pulse

        // Create the notification manager
        assert notificationManager != null;
        notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
        notificationManager.createNotificationChannel(notificationChannel);

        // Send the notification
        notificationManager.notify(0, notificationBuilder.build());
    }
}
