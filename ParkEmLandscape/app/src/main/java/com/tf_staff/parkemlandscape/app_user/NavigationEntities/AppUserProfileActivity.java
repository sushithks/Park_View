package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.Models.UserParkingHistoryModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppUserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    EditText user_username, user_name, user_email, user_phone_number;
    EditText passwordEditText;
    Button qrButton;
    int feedbackCount = 0;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user_username = (EditText) findViewById(R.id.user_username_display);
        user_name = (EditText) findViewById(R.id.user_name_display);
        user_email = (EditText) findViewById(R.id.user_email_display);
        user_phone_number = (EditText) findViewById(R.id.user_phone_display);
        passwordEditText = (EditText) findViewById(R.id.user_password_display);
        findViewById(R.id.buttonUpdateUserProfile).setOnClickListener(this);
        updateFields();
    }

    private void updateFields() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.getUserInfo(sharedPreferences.getString(AppConstants.USER_NAME, "")).enqueue(new Callback<AppUserInfo>() {
            @Override
            public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                AppUserInfo appUserInfo = response.body();
                if (appUserInfo.getStatus().equals("valid")) {
                    user_username.setText(appUserInfo.getAppUserUsername());
                    user_name.setText(appUserInfo.getAppUserName());
                    user_email.setText(appUserInfo.getAppUserEmail());
                    user_phone_number.setText(appUserInfo.getAppUserPhoneNumber());
                    passwordEditText.setText(appUserInfo.getAppUserPassword());
                } else {
                    Toast.makeText(AppUserProfileActivity.this, appUserInfo.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppUserInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AppUserProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_4user_2park) {
            Intent intent = new Intent(this, AppUserNearByParkingActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_wallet) {
            Intent intent = new Intent(this, AppUserWalletActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_history) {
            Intent intent = new Intent(this, UserParkingHistoryActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_booking) {
            Intent intent = new Intent(this, UserListBookingsActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_edit_profile) {
            Intent intent = new Intent(this, AppUserProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(AppUserProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_qr_code) {
            Retrofit retrofit = new MyRetrofitClient().getRetrofit();
            WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
            webServiveAPIs.getQrCode(userName).enqueue(new Callback<AppUserInfo>() {
                @Override
                public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                    AppUserInfo appUserInfo = response.body();
                    if (appUserInfo != null) {
                        Dialog dialog = new Dialog(AppUserProfileActivity.this);
                        dialog.setTitle("QrCode");
                        dialog.setContentView(R.layout.dialog_qr_code_view);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.qrCodeImageView);
                        Picasso.with(AppUserProfileActivity.this)
                                .load(AppConstants.URL + appUserInfo.getUrl())
//                                .placeholder(R.drawable.error)
                                .error(R.drawable.error)
                                .into(imageView);
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<AppUserInfo> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //   super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), AppUserNearByParkingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(final View view) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("name", user_name.getText().toString());
                jsonObject.put("username", user_username.getText().toString());
                jsonObject.put("password", passwordEditText.getText().toString());
                jsonObject.put("email", user_email.getText().toString());
                jsonObject.put("number", user_phone_number.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Retrofit retrofit = new MyRetrofitClient().getRetrofit();
            WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
            webServiveAPIs.userEditProfile(jsonObject).enqueue(new Callback<AppUserInfo>() {
                @Override
                public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                    AppUserInfo appUserInfo = response.body();
                    String status = appUserInfo.getStatus();
                    if (status.equals("success")) {
                        Toast.makeText(AppUserProfileActivity.this, "PROFILE UPDATED", Toast.LENGTH_SHORT).show();
                        updateFields();
                    } else {
                        Toast.makeText(AppUserProfileActivity.this, status, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppUserInfo> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(AppUserProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        }
}
