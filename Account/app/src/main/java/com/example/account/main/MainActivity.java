package com.example.account.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.example.account.R;
import com.example.account.accounts.AllAccountsActivity;
import com.example.account.accounts.ManageAccountsActivity;
import com.example.account.notifications.NotificationReceiver;
import com.example.account.services.ManageServicesActivity;
import com.example.account.alerts.AlertsActivity;
import com.example.account.help.HelpActivity;
import com.example.account.settings.SettingsActivity;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    // Initialise content view for UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set onClick event listeners for buttons, image-buttons, and image-views
        Button btnViewAll = findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(v -> {
            Intent viewAllIntent = new Intent(this, AllAccountsActivity.class);
            viewAllIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(viewAllIntent);
        });

        Button btnManageAccounts = findViewById(R.id.btnManageAccounts);
        btnManageAccounts.setOnClickListener(v -> {
            Intent manageAccountsIntent = new Intent(this, ManageAccountsActivity.class);
            manageAccountsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(manageAccountsIntent);
        });

        Button btnManageServices = findViewById(R.id.btnManageServices);
        btnManageServices.setOnClickListener(v -> {
            Intent manageServicesIntent = new Intent(this, ManageServicesActivity.class);
            manageServicesIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(manageServicesIntent);
        });

        Button btnAlerts = findViewById(R.id.btnAlerts);
        btnAlerts.setOnClickListener(v -> {
            Intent alertsIntent = new Intent(this, AlertsActivity.class);
            alertsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(alertsIntent);
        });

        ImageButton btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Intent helpIntent = new Intent(this, HelpActivity.class);
            helpIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(helpIntent);
        });

        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(settingsIntent);
        });


        ImageView imgAlertStatus = findViewById(R.id.imgAlertStatus);
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean anyAlertsActive = sharedPreferences.getBoolean("anyAlertsActive", false);

        if (anyAlertsActive) {
            imgAlertStatus.setImageResource(R.drawable.baseline_warning_24);
        } else {
            imgAlertStatus.setImageResource(R.drawable.baseline_check_24);
        }

        imgAlertStatus.setOnClickListener(v -> {
            if (anyAlertsActive) {
                Toast.makeText(getApplicationContext(), "There are alerts to review!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "No security alerts to review", Toast.LENGTH_LONG).show();
            }
        });



        // Call function to schedule weekly notification
        scheduleNotification(this);

    }


    protected void onResume(Bundle savedInstanceState) {
        super.onResume();
        onCreate(savedInstanceState);
    }

    public void scheduleNotification(Context context) {
        // Get the shared preference variable
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean anyAlertsActive = sharedPreferences.getBoolean("anyAlertsActive", false);

        // Set the time interval
        final int NOTIFICATION_INTERVAL_DAYS = 7; // 1 week

        // Get the current time
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(System.currentTimeMillis());

        // Set the time for notification to go off (next week)
        Calendar nextWeek = Calendar.getInstance();
        nextWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        nextWeek.set(Calendar.HOUR_OF_DAY, 20); // Set to 20:00 (8 PM)
        nextWeek.set(Calendar.MINUTE, 0);
        nextWeek.set(Calendar.SECOND, 0);

        // If the current time is already past the notification time for this week, schedule the notification for the next week
        if (nextWeek.before(currentTime)) {
            nextWeek.add(Calendar.DAY_OF_MONTH, NOTIFICATION_INTERVAL_DAYS);
        }

        // If a notification is to be sent, due to all alerts being active...
        if (anyAlertsActive) {
            // Get the notification receiver, pending intent
            Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

            // Schedule the next notification (INTERVAL_DAY is 1 day so multiply by NOTIFICATION_INTERVAL_DAYS)
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextWeek.getTimeInMillis(), NOTIFICATION_INTERVAL_DAYS * AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}