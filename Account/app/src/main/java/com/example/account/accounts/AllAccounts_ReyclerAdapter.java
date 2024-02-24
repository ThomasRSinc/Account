package com.example.account.accounts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.account.R;
import com.example.account.database.DBHelper;

import java.io.IOException;
import java.net.URL;


public class AllAccounts_ReyclerAdapter extends RecyclerView.Adapter<AllAccounts_ReyclerAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor_accounts;
    private Cursor cursor_services;


    public AllAccounts_ReyclerAdapter(Context context, Cursor accountsCursor, Cursor servicesCursor) {
        this.context = context;
        this.cursor_accounts = accountsCursor;
        this.cursor_services = servicesCursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_all_accounts, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Move the cursor to the desired position
        if (cursor_accounts.moveToPosition(position)) {
            // Retrieve the data from the cursors
            @SuppressLint("Range")
            String accountName = cursor_accounts.getString(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_NAME));
            String loginEmail = cursor_accounts.getString(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_LOGIN_EMAIL));
            String loginPhone = cursor_accounts.getString(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_LOGIN_PHONE));
            String isSSO = cursor_accounts.getString(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_LOGIN_SSO));
            Integer usesPassword = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_USES_PASSWORD));
            Integer securityEmail = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_EMAIL));
            Integer securityPhone = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_PHONE));
            Integer securityOTP = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_OTP));
            Integer securityKey = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_KEY));
            Integer securityQuestions = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_QUESTIONS));
            Integer securityBackupCodes = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_BACKUP_CODES));
            Integer securityAppPinCode = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_APP_PIN_CODE));
            Integer securityAppBiometrics = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_APP_BIOMETRICS));
            Integer securityAppNotifications = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_APP_NOTIFICATIONS));
            String securityOther = cursor_accounts.getString(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SECURITY_OTHER));


            // Set the data to the views in the row
            holder.accountNameTextView.setText(accountName);
            holder.accountEmailTextView.setText(loginEmail);
            holder.accountPhoneTextView.setText(loginPhone);

            // If the account doesn't have an entry in the SSO field - hide the corresponding image, but still occupy the space
            if (isSSO == null) {
                holder.accountSSOImageView.setVisibility(View.INVISIBLE);
            }

            if (usesPassword == 0) {
                holder.accountUsesPasswordImageView.setVisibility(View.INVISIBLE);
            }

            if (securityEmail == 0) {
                holder.accountSecurityEmailImageView.setVisibility(View.INVISIBLE);
            }

            if (securityPhone == 0) {
                holder.accountSecurityPhoneImageView.setVisibility(View.INVISIBLE);
            }

            if (securityKey == 0) {
                holder.accountSecurityKeyImageView.setVisibility(View.INVISIBLE);
            }

            if (securityAppPinCode == 0 && securityAppBiometrics == 0 && securityAppNotifications == 0) {
                holder.accountSecurityAppImageView.setVisibility(View.INVISIBLE);
            }

            if (securityOTP == 0 && securityQuestions == 0 && securityBackupCodes == 0 && securityOther == null) {
                holder.accountSecurityGeneralImageView.setVisibility(View.INVISIBLE);
            }

            // Set the cursor pointer to the correct service ID record, based on the linked service ID in the accounts table record
            String serviceDomain = "";
            int accountsServiceID = cursor_accounts.getInt(cursor_accounts.getColumnIndex(DBHelper.ACCOUNTS_SERVICE_ID));
            if (cursor_services.moveToFirst()) {
                do {
                    int servicesServiceID = cursor_services.getInt(cursor_services.getColumnIndex(DBHelper.SERVICES_ID));
                    if (servicesServiceID == accountsServiceID) {
                        serviceDomain = cursor_services.getString(cursor_services.getColumnIndex(DBHelper.SERVICES_DOMAIN));
                        break;
                    }
                } while (cursor_services.moveToNext());
            }

            // String serviceDomain = cursor_services.getString(cursor_services.getColumnIndex(DBHelper.SERVICES_DOMAIN));

            // Set the image using the loadImageTask (API stuff)
            String imageUrl = "https://logo.clearbit.com/" + serviceDomain;
            LoadImageTask loadImageTask = new LoadImageTask(holder.serviceLogoImageView);
            loadImageTask.execute(imageUrl);

        }       // Setting the data goes in here
    }                                                             // e.g., holder.accountNameTextView.setText(accountName);

    @Override
    public int getItemCount() {
        return cursor_accounts.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accountNameTextView, accountEmailTextView, accountPhoneTextView;
        public ImageView serviceLogoImageView, accountSSOImageView, accountUsesPasswordImageView, accountSecurityEmailImageView, accountSecurityPhoneImageView, accountSecurityKeyImageView, accountSecurityAppImageView, accountSecurityGeneralImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            serviceLogoImageView = itemView.findViewById(R.id.serviceLogo);
            accountNameTextView = itemView.findViewById(R.id.accountName);
            accountEmailTextView = itemView.findViewById(R.id.accountEmail);
            accountPhoneTextView = itemView.findViewById(R.id.accountPhone);
            accountSSOImageView = itemView.findViewById(R.id.accountSSO);
            accountUsesPasswordImageView = itemView.findViewById(R.id.accountUsesPassword);
            accountSecurityEmailImageView = itemView.findViewById(R.id.accountSecurityEmail);
            accountSecurityPhoneImageView = itemView.findViewById(R.id.accountSecurityPhone);
            accountSecurityKeyImageView = itemView.findViewById(R.id.accountSecurityKey);
            accountSecurityAppImageView = itemView.findViewById(R.id.accountSecurityApp);
            accountSecurityGeneralImageView = itemView.findViewById(R.id.accountSecurityGeneral);
        }
    }




    ///   Loading Image Using API      ///

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            // This is "https://logo.clearbit.com/" + serviceDomain

            // Use API, get logo using domain if possible, and return it
            try {
                URL url = new URL(imageUrl);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Return null otherwise
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // If logo was found, use that
            if (result != null) {
                imageView.setImageBitmap(result);
                imageView.setAlpha(1f);
            } // Otherwise, will just use the default image (faded no image icon)
        }
    }


}
