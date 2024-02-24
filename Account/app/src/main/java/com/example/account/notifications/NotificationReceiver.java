package com.example.account.notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.account.main.MainActivity;
import com.example.account.settings.SettingsActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check that notifications are user-enabled before trying to send them
        if (SettingsActivity.notificationsTurnedOn) {
            NotificationHelper notificationHelper = new NotificationHelper(context);

            // Spawn the notification in MainActivity - that controls how often it appears
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            notificationHelper.createNotification();
        }
    }
}
