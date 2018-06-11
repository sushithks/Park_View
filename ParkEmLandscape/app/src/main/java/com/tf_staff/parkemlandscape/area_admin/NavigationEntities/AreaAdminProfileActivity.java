package com.tf_staff.parkemlandscape.area_admin.NavigationEntities;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AreaAdminInfo;
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

import static com.tf_staff.parkemlandscape.R.id.drawer_layout;


public class AreaAdminProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, OnMapReadyCallback, View.OnClickListener {

    EditText parking_name;
    EditText area_admin_username;
    EditText area_admin_email;
    EditText area_admin_number;
    EditText twoWheelerSlots;
    EditText fourWheelerSlots;
    EditText twoWheelerPriceForOneHour;
    EditText fourWheelerPriceForOneHour;
    EditText twoWheelerPriceAfterOneHour;
    EditText fourWheelerPriceAfetrOneHour;
    EditText passwordEditText;
    double sourceLat;
    double sourceLong;
    Switch aSwitch ;
    TextView status;
    int switchStatus;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        parking_name = (EditText) findViewById(R.id.textViewProfileAdminParkingName);
        passwordEditText = (EditText) findViewById(R.id.textViewProfileAdminPassword);
//        area_admin_username = (EditText) findViewById(R.id.textViewProfileAdminName);

        area_admin_email = (EditText) findViewById(R.id.textViewProfileAdminEmail);

        area_admin_number = (EditText) findViewById(R.id.textViewProfileAdminPhone);

        twoWheelerSlots = (EditText) findViewById(R.id.textViewProfileTwoWheelerSlots);

        fourWheelerSlots = (EditText) findViewById(R.id.textViewProfileFourWheelerSlots);

        twoWheelerPriceForOneHour = (EditText) findViewById(R.id.textViewProfileTwoWheelerPriceForOneHour);

        fourWheelerPriceForOneHour = (EditText) findViewById(R.id.textViewProfileFourWheelerPriceForOneHour);

        twoWheelerPriceAfterOneHour = (EditText) findViewById(R.id.textViewProfileTwoWheelerPriceAfterOneHour);

        fourWheelerPriceAfetrOneHour = (EditText) findViewById(R.id.textViewProfileFourWheelerPriceAfterOneHour);

        findViewById(R.id.buttonUpadteAdminProfile).setOnClickListener(this);

//        get switch priority

        aSwitch = (Switch) findViewById(R.id.switch1);
        String switchStatus = sharedPreferences.getString(AppConstants.ONLINE, "default_value");
        if (!switchStatus.equals("default_value")){
            if (switchStatus.equals("true")){
                aSwitch.setChecked(true);
//            } else {
//                aSwitch.setChecked(false);
            }
        }
        status = (TextView) findViewById(R.id.online);

        aSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.admin_profile_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
//            super.onBackPressed();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_parking_portal) {
            Intent intent = new Intent(this, AreaAdminParkingPortalActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, AreaAdminParkingHistoryActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_log_out) {
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.USER_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
//        else if (id == R.id.nav_edit_profile) {
//            Intent intent = new Intent(this, AreaAdminEditProfileActivityLevelOne.class);
//            startActivity(intent);

//        }
//        else if (id == R.id.nav_change_password) {
//            Intent intent = new Intent(this, AreaAdminProfileActivity.class);
//            startActivity(intent);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(aSwitch.isChecked()){
            status.setText("Online");
            switchStatus = 1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AppConstants.ONLINE, "true");
            editor.apply();
//            status.setTextColor(Color.GREEN);
        } else {
            status.setText("Offline");
            switchStatus = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AppConstants.ONLINE, "false");
            editor.apply();
//            status.setTextColor(Color.RED);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateFields();
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                final Dialog dialog = new Dialog(AreaAdminProfileActivity.this);
                dialog.setContentView(R.layout.dialog_confirm_map_location);
                dialog.setTitle("Confirm");
                dialog.show();
                Button okButton = (Button) dialog.findViewById(R.id.buttonOk);
                Button cancelButton = (Button) dialog.findViewById(R.id.buttonCancel);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sourceLat = latLng.latitude;
                        sourceLong = latLng.longitude;
                        Toast.makeText(AreaAdminProfileActivity.this, sourceLat + "," + sourceLong, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void updateFields() {

        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.getAreaAdminInfo(sharedPreferences.getString(AppConstants.USER_NAME, "")).enqueue(new Callback<AreaAdminInfo>() {
            @Override
            public void onResponse(Call<AreaAdminInfo> call, Response<AreaAdminInfo> response) {
                AreaAdminInfo areaAdminInfo = response.body();
                if (areaAdminInfo.getStatus().equals("valid")) {
                    // Add a marker in Sydney and move the camera
                    sourceLat = Double.parseDouble(areaAdminInfo.getLat());
                    sourceLong = Double.parseDouble(areaAdminInfo.getLongi());
                    LatLng loc = new LatLng(Double.parseDouble(areaAdminInfo.getLat()), Double.parseDouble(areaAdminInfo.getLongi()));
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(loc));
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(loc, 15);
                    mMap.moveCamera(cameraUpdate);
                    parking_name.setText(areaAdminInfo.getAreaAdminName());
//                    area_admin_username.setText(areaAdminInfo.getAreaAdminUsername());
                    passwordEditText.setText(areaAdminInfo.getAreaAdminPassword());
                    area_admin_email.setText(areaAdminInfo.getAreaAdminEmail());
                    area_admin_number.setText(areaAdminInfo.getAreaAdminPhoneNumber());
                    twoWheelerSlots.setText(areaAdminInfo.getTwoWheelerNoOfSlots());
                    fourWheelerSlots.setText(areaAdminInfo.getFourWheelerNoOfSlots());
                    twoWheelerPriceForOneHour.setText(areaAdminInfo.getTwoWheelerPriceFor1Hr());
                    fourWheelerPriceForOneHour.setText(areaAdminInfo.getFourWheelerPriceFor1Hr());
                    twoWheelerPriceAfterOneHour.setText(areaAdminInfo.getTwoWheelerPriceAfter1Hr());
                    fourWheelerPriceAfetrOneHour.setText(areaAdminInfo.getFourWheelerPriceAfter1Hr());
                } else {
                    Toast.makeText(AreaAdminProfileActivity.this, areaAdminInfo.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AreaAdminInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AreaAdminProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", parking_name.getText().toString());
            jsonObject.put("area_admin_username", sharedPreferences.getString(AppConstants.USER_NAME, ""));
            jsonObject.put("area_admin_email", area_admin_email.getText().toString());
            jsonObject.put("area_admin_number", area_admin_number.getText().toString());
            jsonObject.put("area_admin_password", passwordEditText.getText().toString());
            jsonObject.put("twoWheelerSlots", twoWheelerSlots.getText().toString());
            jsonObject.put("lat", String.valueOf(sourceLat));
            jsonObject.put("longi", String.valueOf(sourceLong));
            jsonObject.put("fourWheelerSlots", fourWheelerSlots.getText().toString());
            jsonObject.put("twoWheelerPriceForOneHour", twoWheelerPriceForOneHour.getText().toString());
            jsonObject.put("fourWheelerPriceForOneHour", fourWheelerPriceForOneHour.getText().toString());
            jsonObject.put("twoWheelerPriceAfterOneHour", twoWheelerPriceAfterOneHour.getText().toString());
            jsonObject.put("fourWheelerPriceAfetrOneHour", fourWheelerPriceAfetrOneHour.getText().toString());
            jsonObject.put("user_type", "PARKING AREA ADMIN");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.areaAdminEditProfile(jsonObject).enqueue(new Callback<AreaAdminInfo>() {
            @Override
            public void onResponse(Call<AreaAdminInfo> call, Response<AreaAdminInfo> response) {
                AreaAdminInfo areaAdminInfo = response.body();
                String status = areaAdminInfo.getStatus();
                if (status.equals("success")) {
                    Toast.makeText(AreaAdminProfileActivity.this, "PROFILE UPDATED", Toast.LENGTH_SHORT).show();
                    updateFields();
                } else {
                    Toast.makeText(AreaAdminProfileActivity.this, status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AreaAdminInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AreaAdminProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
