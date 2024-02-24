package com.example.account.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.account.R;
import com.example.account.database.DBManager;

public class AddAccountActivity extends AppCompatActivity {
    private EditText editTextServiceID, editTextAccountName, editTextLoginEmail, editTextLoginPhone, editTextLoginSSO, editTextOtherSecurity;
    private CheckBox checkboxPassword, checkboxEmail, checkboxPhone, checkboxOTP, checkboxSecurityKey, checkboxQuestions, checkboxBackupCodes, checkboxAppPinCode, checkboxAppBiometrics, checkboxAppNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        editTextServiceID = findViewById(R.id.inputServiceID);
        editTextAccountName = findViewById(R.id.inputName);
        editTextLoginEmail = findViewById(R.id.inputEmail);
        editTextLoginPhone = findViewById(R.id.inputPhone);
        editTextLoginSSO = findViewById(R.id.inputSSO);
        editTextOtherSecurity = findViewById(R.id.inputOtherSecurity);
        checkboxPassword = findViewById(R.id.chkPassword);
        checkboxEmail = findViewById(R.id.chkEmail);
        checkboxPhone = findViewById(R.id.chkPhone);
        checkboxOTP = findViewById(R.id.chkOTP);
        checkboxSecurityKey = findViewById(R.id.chkSecurityKey);
        checkboxQuestions = findViewById(R.id.chkQuestions);
        checkboxBackupCodes = findViewById(R.id.chkBackupCodes);
        checkboxAppPinCode = findViewById(R.id.chkAppPinCode);
        checkboxAppBiometrics = findViewById(R.id.chkAppBiometrics);
        checkboxAppNotifications = findViewById(R.id.chkAppNotifications);
    }


    public void saveToDatabase(View view) {
        String serviceID_string = editTextServiceID.getText().toString();
        String accountName = editTextAccountName.getText().toString();
        String loginEmail = editTextLoginEmail.getText().toString();
        String loginPhone = editTextLoginPhone.getText().toString();
        String loginSSO_string = editTextLoginSSO.getText().toString();
        Integer usesPassword = checkboxPassword.isChecked() ? 1 : 0;
        Integer securityEmail = checkboxEmail.isChecked() ? 1 : 0;
        Integer securityPhone = checkboxPhone.isChecked() ? 1 : 0;
        Integer securityOTP = checkboxOTP.isChecked() ? 1 : 0;
        Integer securityKey = checkboxSecurityKey.isChecked() ? 1 : 0;
        Integer securityQuestions = checkboxQuestions.isChecked() ? 1 : 0;
        Integer securityBackupCodes = checkboxBackupCodes.isChecked() ? 1 : 0;
        Integer securityAppPinCode = checkboxAppPinCode.isChecked() ? 1 : 0;
        Integer securityAppBiometrics = checkboxAppBiometrics.isChecked() ? 1 : 0;
        Integer securityAppNotifications = checkboxAppNotifications.isChecked() ? 1 : 0;
        String securityOther = editTextOtherSecurity.getText().toString();


        // If the required (not null) input boxes actually have something in them...
        if (!serviceID_string.isEmpty() && !accountName.isEmpty()) {
            Integer serviceID = Integer.parseInt(serviceID_string);
            Integer loginSSO = 0;       // return as 0, so the DBManger function will not input a value for that field
            if (!loginSSO_string.isEmpty()) {
                loginSSO = Integer.parseInt(loginSSO_string);
            }

            DBManager dbManager = new DBManager(AddAccountActivity.this);
            dbManager.open();

            dbManager.insertAccounts(accountName, serviceID, loginEmail, loginPhone, loginSSO, usesPassword, securityEmail, securityPhone, securityOTP, securityKey, securityQuestions, securityBackupCodes, securityAppPinCode, securityAppBiometrics, securityAppNotifications, securityOther);

            dbManager.close();

            Toast.makeText(getApplicationContext(), "Account added successfully", Toast.LENGTH_SHORT).show();

            // Create an intent to navigate back to the Manage Services page
            Intent intent = new Intent(this, ManageAccountsActivity.class);
            startActivity(intent);
            finish();       // Remove the current activity from the stack

        } else {    // Otherwise, display an error toast message
            Toast.makeText(getApplicationContext(), "ERROR - ServiceID and AccountName require a value", Toast.LENGTH_SHORT).show();
        }

    }
}