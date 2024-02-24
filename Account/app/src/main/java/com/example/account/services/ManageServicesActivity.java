package com.example.account.services;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.account.R;
import com.example.account.database.DBHelper;
import com.example.account.database.DBManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManageServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);


        // Create clickable button that will send to the addServiceActivity activity
        FloatingActionButton fab = findViewById(R.id.btnAddEntryServices);
        fab.setOnClickListener(v -> {
            Intent addServiceIntent = new Intent(this, AddServiceActivity.class);
            addServiceIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(addServiceIntent);
        });


        // Display database entries ...

        // Create an instance of DBManager
        DBManager dbManager = new DBManager(ManageServicesActivity.this);

        // Fetch the cursor from the DBManager class
        dbManager.open();
        Cursor cursor = dbManager.fetchServices();

        LinearLayout columnHeadingsContainer = findViewById(R.id.columnHeadingsContainer_Services);
        LinearLayout entriesContainer = findViewById(R.id.entriesContainer_Services);

        if (cursor.moveToFirst()) {
            // Fetch column names
            String[] columnNames = cursor.getColumnNames();

            // Create TextViews for column headings dynamically
            for (String columnName : columnNames) {
                TextView headingTextView = new TextView(this);
                headingTextView.setText(columnName);
                headingTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                columnHeadingsContainer.addView(headingTextView);
            }

            // Iterate over the cursor and create TextViews dynamically for each entry
            do {
                LinearLayout entryLayout = new LinearLayout(this);
                entryLayout.setOrientation(LinearLayout.HORIZONTAL);
                entryLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                for (String columnName : columnNames) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    String entryData = cursor.getString(columnIndex);
                    TextView entryTextView = new TextView(this);
                    entryTextView.setText(entryData);
                    entryTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                    LinearLayout.LayoutParams textLayoutParams = (LinearLayout.LayoutParams) entryTextView.getLayoutParams();
                    textLayoutParams.setMargins(0, 0, 0, 40);
                    entryTextView.setLayoutParams(textLayoutParams);
                    entryLayout.addView(entryTextView);
                }

                // Get the service ID from the clicked row
                @SuppressLint("Range") int serviceID = cursor.getInt(cursor.getColumnIndex(DBHelper.SERVICES_ID));

                // Add OnClickListener to the entry row
                entryLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create and show the AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageServicesActivity.this);
                        builder.setTitle("Delete Service");
                        builder.setMessage("Are you sure you want to delete this entry?");

                        // Set the positive button (Yes)
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Call the deleteServicesEntry() method
                                dbManager.deleteServiceEntry(serviceID, false);

                                // Display confirmation toast message
                                Toast.makeText(getApplicationContext(), "Record deleted.", Toast.LENGTH_SHORT).show();

                                // Recreate the activity, to refresh the UI
                                recreate();
                            }
                        });

                        // Set the negative button (No)
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog and do nothing
                                dialog.dismiss();
                            }
                        });

                        // Create and show the AlertDialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                entriesContainer.addView(entryLayout);
            } while (cursor.moveToNext());

            cursor.close();

        } else {
            TextView errorTextView = new TextView(this);
            errorTextView.setText(R.string.databaseErrorMessage);
            errorTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            entriesContainer.addView(errorTextView);
        }

    }
}