package com.example.account.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import android.os.Bundle;
import android.widget.Button;

import com.example.account.R;
import com.example.account.notifications.NotificationHelper;


public class SettingsActivity extends AppCompatActivity {

    public static boolean notificationsTurnedOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.settingsFragment, new SettingsFragment()).commit();
        }

        // When button pressed...
        Button testNotification = findViewById(R.id.btnTestNotification);
        testNotification.setOnClickListener(v -> {
            if (notificationsTurnedOn) {
                // Call the create notification function
                NotificationHelper notificationHelper = new NotificationHelper(this);
                notificationHelper.createNotification();
            }  // Otherwise, do nothing
        });
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            // Activity (fragment) is set in root_prefs.xml
            setPreferencesFromResource(R.xml.root_prefs, rootKey);

            // Get the notification preference toggle
            SwitchPreferenceCompat notificationSwitch = findPreference("notificationsToggle");

            // If the notification switch is off, then no notifications for the user
            if (notificationSwitch != null) {
                notificationSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                    boolean isChecked = true;
                    if (newValue instanceof Boolean)
                        isChecked = (Boolean) newValue;

                    notificationsTurnedOn = isChecked;

                    return true;
                });
            }
        }
    }
}