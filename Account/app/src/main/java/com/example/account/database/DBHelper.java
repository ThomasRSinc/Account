package com.example.account.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DBHelper is used to create databases, manage database structure, and provide database access
public class DBHelper extends SQLiteOpenHelper {
    // Database name and version
    public static final String DB_NAME = "Account.db";
    public static final int DB_VERSION = 1;

    // Table names
    public static final String TABLE_NAME_SERVICES = "dbServices";
    public static final String TABLE_NAME_ACCOUNTS = "dbAccounts";


    // Strings for services table fields
    public static final String SERVICES_ID = "serviceID";
    public static final String SERVICES_NAME = "serviceName";
    public static final String SERVICES_DOMAIN = "webDomain";

    // Strings for accounts table fields
    public static final String ACCOUNTS_ID = "accountID";
    public static final String ACCOUNTS_NAME = "accountName";
    public static final String ACCOUNTS_SERVICE_ID = "serviceID";
    public static final String ACCOUNTS_LOGIN_EMAIL = "login_email";
    public static final String ACCOUNTS_LOGIN_PHONE = "login_phone";
    public static final String ACCOUNTS_LOGIN_SSO = "login_SSO";
    public static final String ACCOUNTS_USES_PASSWORD = "usesPassword";
    public static final String ACCOUNTS_SECURITY_EMAIL = "security_email";
    public static final String ACCOUNTS_SECURITY_PHONE = "security_phone";
    public static final String ACCOUNTS_SECURITY_OTP = "security_OTP";
    public static final String ACCOUNTS_SECURITY_KEY = "security_key";
    public static final String ACCOUNTS_SECURITY_QUESTIONS = "security_questions";
    public static final String ACCOUNTS_SECURITY_BACKUP_CODES = "security_backupCodes";
    public static final String ACCOUNTS_SECURITY_APP_PIN_CODE = "security_appPinCode";
    public static final String ACCOUNTS_SECURITY_APP_BIOMETRICS = "security_appBiometrics";
    public static final String ACCOUNTS_SECURITY_APP_NOTIFICATIONS = "security_appNotifications";
    public static final String ACCOUNTS_SECURITY_OTHER = "security_other";



    // SQL table creation statements
    private static final String CREATE_TABLE_SERVICES =
            "CREATE TABLE " + TABLE_NAME_SERVICES + " (" +
                    SERVICES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SERVICES_NAME + " TEXT NOT NULL, " +
                    SERVICES_DOMAIN + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_ACCOUNTS =
            "CREATE TABLE " + TABLE_NAME_ACCOUNTS + " (" +
                    ACCOUNTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ACCOUNTS_NAME + " TEXT NOT NULL, " +
                    ACCOUNTS_SERVICE_ID + " INTEGER NOT NULL REFERENCES " + TABLE_NAME_SERVICES + "(" + SERVICES_ID + "), " +
                    ACCOUNTS_LOGIN_EMAIL + " TEXT, " +
                    ACCOUNTS_LOGIN_PHONE + " TEXT, " +
                    ACCOUNTS_LOGIN_SSO + " INTEGER REFERENCES " + TABLE_NAME_ACCOUNTS + "(" + ACCOUNTS_ID + "), " +
                    ACCOUNTS_USES_PASSWORD + " INTEGER NOT NULL CHECK(" + ACCOUNTS_USES_PASSWORD + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_EMAIL + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_EMAIL + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_PHONE + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_PHONE + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_OTP + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_OTP + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_KEY + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_KEY + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_QUESTIONS + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_QUESTIONS + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_BACKUP_CODES + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_BACKUP_CODES + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_APP_PIN_CODE + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_APP_PIN_CODE + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_APP_BIOMETRICS + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_APP_BIOMETRICS + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_APP_NOTIFICATIONS + " INTEGER NOT NULL CHECK(" + ACCOUNTS_SECURITY_APP_NOTIFICATIONS + " IN(0,1)), " +
                    ACCOUNTS_SECURITY_OTHER + " TEXT " + ")";


    // Create a helper object
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Create tables (statement strings are above)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVICES);
        db.execSQL(CREATE_TABLE_ACCOUNTS);
    }

    // Create new tables (for database schema upgrades)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNTS);
        onCreate(db);
    }


}
