package com.example.account.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBManager {
    private DBHelper dbHelper = null;
    private SQLiteDatabase database;
    private final Context context;

    // Allows the DBManager class to be referenced
    public DBManager(Context c) {
        context = c;
    }

    // Open the database
    public void open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }


    // Insert an entry into the services table
    public void insertServices(String serviceName, String webDomain) {
        ContentValues contentValues = new ContentValues();
        // Service ID gets added automatically
        contentValues.put(DBHelper.SERVICES_NAME, serviceName);
        contentValues.put(DBHelper.SERVICES_DOMAIN, webDomain);
        database.insert(DBHelper.TABLE_NAME_SERVICES, null, contentValues);
    }

    // Insert an entry into the accounts table
    public void insertAccounts(String accountName, Integer serviceID, String login_email, String login_phone, Integer login_SSO, Integer usesPassword, Integer security_email, Integer security_phone, Integer security_OTP, Integer security_key, Integer security_questions, Integer security_backupCodes, Integer security_appPinCode, Integer security_appBiometrics, Integer security_appNotifications, String security_other) {
        ContentValues contentValues = new ContentValues();
        // Service ID gets added automatically
        contentValues.put(DBHelper.ACCOUNTS_NAME, accountName);
        contentValues.put(DBHelper.ACCOUNTS_SERVICE_ID, serviceID);

        // These values are not required in the database, and can be passed in as blank/0 to indicate 'no value'
        if(!login_email.isEmpty()) {
            contentValues.put(DBHelper.ACCOUNTS_LOGIN_EMAIL, login_email);
        }
        if(!login_phone.isEmpty()) {
            contentValues.put(DBHelper.ACCOUNTS_LOGIN_PHONE, login_phone);
        }
        if(login_SSO != 0) {
            contentValues.put(DBHelper.ACCOUNTS_LOGIN_SSO, login_SSO);
        }
        if(!security_other.isEmpty()) {
            contentValues.put(DBHelper.ACCOUNTS_SECURITY_OTHER, security_other);
        }

        // These values are required, and should be passed in as either 1 ("Yes") or 0 ("No")
        contentValues.put(DBHelper.ACCOUNTS_USES_PASSWORD, usesPassword);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_EMAIL, security_email);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_PHONE, security_phone);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_OTP, security_OTP);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_KEY, security_key);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_QUESTIONS, security_questions);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_BACKUP_CODES, security_backupCodes);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_APP_PIN_CODE, security_appPinCode);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_APP_BIOMETRICS, security_appBiometrics);
        contentValues.put(DBHelper.ACCOUNTS_SECURITY_APP_NOTIFICATIONS, security_appNotifications);

        // Insert the values into the table
        database.insert(DBHelper.TABLE_NAME_ACCOUNTS, null, contentValues);
    }


    // Fetch all the entries from the services table
    public Cursor fetchServices(){
        // The table columns to select
        String[] columns = new String[] {
                DBHelper.SERVICES_ID,
                DBHelper.SERVICES_NAME,
                DBHelper.SERVICES_DOMAIN
        };

        // The fetching query, with options
        Cursor cursor = database.query(DBHelper.TABLE_NAME_SERVICES,
                columns,
                null,
                null,
                null,
                null,
                null);

        // Ensure that the cursor is positioned at the beginning of the result set
        if (cursor != null){
            cursor.moveToFirst();
        }
        // Return the cursor object containing the query data
        return cursor;
    }

    // Fetch all the entries from the accounts table
    public Cursor fetchAccounts(){
        // The table columns to select
        String[] columns = new String[] {
                DBHelper.ACCOUNTS_ID,
                DBHelper.ACCOUNTS_NAME,
                DBHelper.ACCOUNTS_SERVICE_ID,
                DBHelper.ACCOUNTS_LOGIN_EMAIL,
                DBHelper.ACCOUNTS_LOGIN_PHONE,
                DBHelper.ACCOUNTS_LOGIN_SSO,
                DBHelper.ACCOUNTS_USES_PASSWORD,
                DBHelper.ACCOUNTS_SECURITY_EMAIL,
                DBHelper.ACCOUNTS_SECURITY_PHONE,
                DBHelper.ACCOUNTS_SECURITY_OTP,
                DBHelper.ACCOUNTS_SECURITY_KEY,
                DBHelper.ACCOUNTS_SECURITY_QUESTIONS,
                DBHelper.ACCOUNTS_SECURITY_BACKUP_CODES,
                DBHelper.ACCOUNTS_SECURITY_APP_PIN_CODE,
                DBHelper.ACCOUNTS_SECURITY_APP_BIOMETRICS,
                DBHelper.ACCOUNTS_SECURITY_APP_NOTIFICATIONS,
                DBHelper.ACCOUNTS_SECURITY_OTHER,
        };

        // The fetching query, with options
        Cursor cursor = database.query(DBHelper.TABLE_NAME_ACCOUNTS,
                columns,
                null,
                null,
                null,
                null,
                null);

        // Ensure that the cursor is positioned at the beginning of the result set
        if (cursor != null){
            cursor.moveToFirst();
        }
        // Return the cursor object containing the query data
        return cursor;
    }

    // Delete the entry with the specified service ID, or delete all if bool is True
    public void deleteServiceEntry(Integer serviceID, boolean deleteAllEntries){
        // Can't use wildcard to select all because serviceID is passed as an integer, rather than the needed string for "*"

        if (deleteAllEntries) {
            database.delete(DBHelper.TABLE_NAME_SERVICES, null, null);
            dbHelper.close();
        } else {
            database.delete(DBHelper.TABLE_NAME_SERVICES, DBHelper.SERVICES_ID + " = " + serviceID, null);
        }
    }

    // Delete the entry with the specified account ID, or delete all if bool is True
    public void deleteAccountEntry(Integer accountID, boolean deleteAllEntries){
        if (deleteAllEntries) {
            database.delete(DBHelper.TABLE_NAME_ACCOUNTS, null, null);
            dbHelper.close();
        } else {
            database.delete(DBHelper.TABLE_NAME_ACCOUNTS, DBHelper.ACCOUNTS_ID + " = " + accountID, null);
        }
    }

}
