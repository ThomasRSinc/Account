package com.example.account.alerts;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.account.R;
import com.example.account.database.DBHelper;
import com.example.account.database.DBManager;

public class AlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        updateAlerts();
    }

    public void updateAlerts() {

        boolean anyAlertsActive = false;

        DBManager dbManager = new DBManager(AlertsActivity.this);
        dbManager.open();
        Cursor cursor = dbManager.fetchAccounts();

        // If the accounts table actually contains records, and has returned them in Cursor
        if (cursor != null && cursor.moveToFirst()) {
            // Check if password alerts should be highlighted
            if (checkAnyTrueInColumns(cursor, new String[]{DBHelper.ACCOUNTS_USES_PASSWORD})) {
                findViewById(R.id.imgPassword).setAlpha(1);
                findViewById(R.id.txtPassword).setAlpha(1);
                anyAlertsActive = true;
            } // Otherwise, do nothing (just leave as faded alpha value)

            // Repeat... Checking if email alerts should be active
            if (checkAnyNotNullInColumns(cursor, new String[]{DBHelper.ACCOUNTS_LOGIN_EMAIL})) {
                findViewById(R.id.imgEmail).setAlpha(1);
                findViewById(R.id.txtEmail).setAlpha(1);
                anyAlertsActive = true;
            }

            // Check if app alerts should be active
            if (checkAnyTrueInColumns(cursor, new String[]{DBHelper.ACCOUNTS_SECURITY_APP_PIN_CODE, DBHelper.ACCOUNTS_SECURITY_APP_BIOMETRICS, DBHelper.ACCOUNTS_SECURITY_APP_NOTIFICATIONS})) {
                findViewById(R.id.imgApp).setAlpha(1);
                findViewById(R.id.txtApp).setAlpha(1);
                anyAlertsActive = true;
            }

            // Check if phone alerts should be active
            if (checkAnyNotNullInColumns(cursor, new String[]{DBHelper.ACCOUNTS_LOGIN_PHONE})) {
                findViewById(R.id.imgPhone).setAlpha(1);
                findViewById(R.id.txtPhone).setAlpha(1);
                anyAlertsActive = true;
            }

            // Check if SSO alerts should be active
            if (checkAnyNotNullInColumns(cursor, new String[]{DBHelper.ACCOUNTS_LOGIN_SSO})) {
                findViewById(R.id.imgSSO).setAlpha(1);
                findViewById(R.id.txtSSO).setAlpha(1);
                anyAlertsActive = true;
            }

            // Check if security key alerts should be active
            if (checkAnyTrueInColumns(cursor, new String[]{DBHelper.ACCOUNTS_SECURITY_KEY})) {
                findViewById(R.id.imgSecurityKey).setAlpha(1);
                findViewById(R.id.txtSecurityKey).setAlpha(1);
                anyAlertsActive = true;
            }

            // Check if general/misc security alerts should be active
            if (checkAnyTrueInColumns(cursor, new String[]{DBHelper.ACCOUNTS_SECURITY_BACKUP_CODES, DBHelper.ACCOUNTS_SECURITY_QUESTIONS})) {
                findViewById(R.id.imgGeneralSecurity).setAlpha(1);
                findViewById(R.id.txtGeneralSecurity).setAlpha(1);
                anyAlertsActive = true;
            }
        } // Otherwise, anyAlertsActive is already set to false

        dbManager.close();


        // Save the "anyAlertsActive" Shared Preferences variable value, which gets used elsewhere
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("anyAlertsActive", anyAlertsActive);
        editor.apply();
    }



    // If any of the records contain True in the particular column(s)
    public boolean checkAnyTrueInColumns(Cursor cursor, String[] columnNames) {
        // Assumes that cursor has been checked to ensure not empty first
        if (cursor.moveToFirst()) {
            do {
                for (String columnName : columnNames) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    boolean value = cursor.getInt(columnIndex) == 1;    // Assuming 1 represents true in the table (which it does)
                    if (value) {
                        return true;
                    }
                }
            } while (cursor.moveToNext());
        }

        return false;
    }


    // If any of the records are not null
    public boolean checkAnyNotNullInColumns(Cursor cursor, String[] columnNames) {
        // Assumes that cursor has been checked to ensure not empty first

        if (cursor.moveToFirst()) {
            do {
                for (String columnName : columnNames) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    if (!cursor.isNull(columnIndex)) {
                        return true;
                    }
                }
            } while (cursor.moveToNext());
        }

        return false;
    }

}
