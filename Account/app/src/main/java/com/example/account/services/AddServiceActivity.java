package com.example.account.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.account.R;
import com.example.account.database.DBManager;

public class AddServiceActivity extends AppCompatActivity {

    private EditText editTextServiceName, editTextServiceDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        editTextServiceName = findViewById(R.id.inputServiceName);
        editTextServiceDomain = findViewById(R.id.inputServiceDomain);
    }

    public void saveToDatabase(View view) {
        String serviceName = editTextServiceName.getText().toString();
        String serviceDomain = editTextServiceDomain.getText().toString();

        // If the input boxes actually have something in them...
        if (!serviceName.isEmpty() && !serviceDomain.isEmpty()) {
            DBManager dbManager = new DBManager(AddServiceActivity.this);
            dbManager.open();
            dbManager.insertServices(serviceName, serviceDomain);
            dbManager.close();

            Toast.makeText(getApplicationContext(), "Service added successfully", Toast.LENGTH_SHORT).show();

            // Create an intent to navigate back to the Manage Services page
            Intent intent = new Intent(this, ManageServicesActivity.class);
            startActivity(intent);
            finish();       // Remove the current activity from the stack
        } else {    // Otherwise, display an error toast message
            Toast.makeText(getApplicationContext(), "ERROR - All fields require a value", Toast.LENGTH_SHORT).show();
        }

    }
}