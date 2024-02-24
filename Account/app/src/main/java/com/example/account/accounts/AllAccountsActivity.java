package com.example.account.accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.account.R;
import com.example.account.database.DBManager;

public class AllAccountsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_accounts);

        // Perform database table lookups
        DBManager dbManager = new DBManager(AllAccountsActivity.this);
        dbManager.open();
        Cursor cursor_accounts = dbManager.fetchAccounts();
        Cursor cursor_services = dbManager.fetchServices();

        // If both tables have returned results (only accounts check is needed tbh, but good to check to prevent errors which may have slipped through)
        if (cursor_accounts.moveToFirst() && cursor_services.moveToFirst()) {
            // Initialise RecyclerView and adapter
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            AllAccounts_ReyclerAdapter adapter = new AllAccounts_ReyclerAdapter(this, cursor_accounts, cursor_services);

            // Set the adapter to the RecyclerView
            recyclerView.setAdapter(adapter);
        } else {
            // Otherwise, show the "no accounts to show" message, which is normally fully hidden
            findViewById(R.id.txtAllAccounts).setVisibility(View.VISIBLE);
        }

        dbManager.close();
    }
}