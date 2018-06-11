package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.Models.ParkingBookingModel;
import com.tf_staff.parkemlandscape.Models.ParkingLocationModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppUserNearByParkingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    PlaceAutocompleteFragment manualSearchAutocompleteFragment;

    double sourceLat;
    double sourceLong;
    EditText sourceEditText;
    LocationManager locationManager;
    String provider = LocationManager.NETWORK_PROVIDER;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    double lat;
    double longi;
    String username;
    HashMap<Marker, ParkingLocationModel> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_parking);
        SharedPreferences shr = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        username = shr.getString(AppConstants.USER_NAME, "");
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapForNear);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manualSearchAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.manual_search_autocomplete_fragment_for_parking);
        sourceEditText = ((EditText) manualSearchAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input));
        sourceEditText.setHint("Enter manually");

        sourceEditText.setTextSize(25f);
        sourceEditText.setTextColor(0xffffffff);
        manualSearchAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng latLng = place.getLatLng();
                sourceLat = latLng.latitude;
                sourceLong = latLng.longitude;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                getParkingSpaces(sourceLat, sourceLong);
            }

            @Override
            public void onError(Status status) {
                Log.e("error map: ", status.getStatusMessage());
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_4user_2park) {
            Intent intent = new Intent(this, AppUserNearByParkingActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_user_wallet) {
            Intent intent = new Intent(this, AppUserWalletActivity.class);
            startActivity(intent);
            finish();

        }  else if (id == R.id.nav_user_history) {
            Intent intent = new Intent(this, UserParkingHistoryActivity.class);
            startActivity(intent);
            finish();

        }  else if (id == R.id.nav_user_booking) {
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
            Intent intent = new Intent(AppUserNearByParkingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_qr_code) {
            Retrofit retrofit = new MyRetrofitClient().getRetrofit();
            WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
            webServiveAPIs.getQrCode(username).enqueue(new Callback<AppUserInfo>() {
                @Override
                public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                    AppUserInfo appUserInfo = response.body();
                    if (appUserInfo != null) {
                        Dialog dialog = new Dialog(AppUserNearByParkingActivity.this);
                        dialog.setTitle("QrCode");
                        dialog.setContentView(R.layout.dialog_qr_code_view);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.qrCodeImageView);
                        Log.e("QRCode url", AppConstants.URL+appUserInfo.getUrl());
                        Picasso.with(AppUserNearByParkingActivity.this)
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            } else {
                mMap.setMyLocationEnabled(true);
            }

        } else {
            mMap.setMyLocationEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 1);
            } else {
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
            }
        } else {
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            }
        } else if (requestCode == 1) {
            if (grantResults.length > 0) {
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(AppUserNearByParkingActivity.this, "onlocation changed called", Toast.LENGTH_SHORT).show();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
        mMap.animateCamera(cameraUpdate);
        lat = location.getLatitude();
        longi = location.getLongitude();
        getParkingSpaces(location.getLatitude(), location.getLongitude());
        locationManager.removeUpdates(this);
    }

    private void plotMarkers(List<ParkingLocationModel> parkingLocationModels) {
        hashMap = new HashMap<Marker, ParkingLocationModel>();
        hashMap.clear();
        mMap.clear();
        for (int i = 0; i < parkingLocationModels.size(); i++) {
            ParkingLocationModel parkingLocationModel = parkingLocationModels.get(i);
            double lat = Double.parseDouble(parkingLocationModel.getLat());
            double longi = Double.parseDouble(parkingLocationModel.getLongi());
            double dist = Double.parseDouble(parkingLocationModel.getDistance());
            String name = parkingLocationModel.getName();
            String motorBikeSlots = parkingLocationModel.getMotorBikeSlots();
            String carSlots = parkingLocationModel.getCarSlots();
            String bikeMinCharge = parkingLocationModel.getMinimumChargeBike();
            String carMinCharge = parkingLocationModel.getMinimumChargeCar();
            String carAfterOneHr = parkingLocationModel.getCarAfterOneHr();
            String bikeAfterOneHr = parkingLocationModel.getBikeAfterOneHr();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("distance: ");
            stringBuilder.append(dist + " KM" + "\n");
            stringBuilder.append("Bike Slots: ");
            stringBuilder.append(motorBikeSlots + "\n");
            stringBuilder.append("Car Slots:");
            stringBuilder.append(carSlots + "\n");
            stringBuilder.append("Car Charge: ");
            stringBuilder.append(carMinCharge + ", " + carAfterOneHr + "\n");
            stringBuilder.append("Bike Charge: ");
            stringBuilder.append(bikeMinCharge + ", " + bikeAfterOneHr + "\n");
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(lat, longi))
//                    .title(lat + "," + longi)
                    .title(name)
                    .snippet(stringBuilder.toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            Marker marker = mMap.addMarker(markerOptions);
            hashMap.put(marker, parkingLocationModel);
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final ParkingLocationModel parkingLocationModel = hashMap.get(marker);
                final String title = marker.getTitle();
                final String snippet = marker.getSnippet();
                final Dialog dialog = new Dialog(AppUserNearByParkingActivity.this);
                dialog.setTitle("options");
                dialog.setContentView(R.layout.dialog_marker_click);
                dialog.show();
                dialog.findViewById(R.id.infoButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Dialog dialog = new Dialog(AppUserNearByParkingActivity.this);
                        dialog.setTitle("Info");
                        dialog.setContentView(R.layout.dialog_marker_info);
                        TextView textView = (TextView) dialog.findViewById(R.id.infoTextView);
                        textView.setText(snippet);
                        dialog.show();
                    }
                });
                dialog.findViewById(R.id.pathButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Uri uri = Uri.parse("http://maps.google.com/maps?"
                                + "saddr=" + lat + "," + longi + "&daddr=" + title);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                });
                dialog.findViewById(R.id.bookButton).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final String id = parkingLocationModel.getId();
                        Log.e("parkingid", id);
                        final Dialog dialog1 = new Dialog(AppUserNearByParkingActivity.this);
                        dialog1.setContentView(R.layout.dialog_parking_booking);
                        final EditText timeEditText = (EditText) dialog1.findViewById(R.id.timeEditText);
                        final Spinner vehicleTypeSpinner = (Spinner) dialog1.findViewById(R.id.vehicleTypeSpinner);
                        ArrayAdapter adapter = new ArrayAdapter(AppUserNearByParkingActivity.this,
                                android.R.layout.simple_spinner_item, new String[]{"car", "bike"});
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        vehicleTypeSpinner.setAdapter(adapter);
                        dialog1.show();
                        timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    Calendar mcurrentTime = Calendar.getInstance();
                                    final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                    final int minute = mcurrentTime.get(Calendar.MINUTE);
                                    TimePickerDialog mTimePicker;
                                    mTimePicker = new TimePickerDialog(AppUserNearByParkingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                            try {
                                                Date time1 = new SimpleDateFormat("HH:mm").parse(hour + ":" + minute);
                                                Date time2 = new SimpleDateFormat("HH:mm").parse(selectedHour + ":" + selectedMinute);
                                                long diff = time2.getTime() - time1.getTime();
                                                float diffMinutes = diff / (60 * 1000);
                                                if (diffMinutes >= 0 && diffMinutes <= 60) {
                                                    timeEditText.setText(selectedHour + ":" + selectedMinute);
                                                } else {
                                                    Toast.makeText(AppUserNearByParkingActivity.this, "You can book before one hour only", Toast.LENGTH_LONG).show();
                                                }
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, hour, minute, true);//Yes 24 hour time
                                    mTimePicker.setTitle("Select Time");
                                    mTimePicker.show();
                                }
                            }
                        });
                        dialog1.findViewById(R.id.parkingBookingConfirmButton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ParkingBookingModel model = new ParkingBookingModel();
                                boolean flag = true;
                                if (vehicleTypeSpinner.getSelectedItem().toString().equals("car")) {
                                    if (Integer.parseInt(parkingLocationModel.getCarSlots()) == 0) {
                                        flag = false;
                                    }
                                } else {
                                    if (Integer.parseInt(parkingLocationModel.getMotorBikeSlots()) == 0) {
                                        flag = false;
                                    }
                                }
                                if (flag == true) {
                                    model.setType(vehicleTypeSpinner.getSelectedItem().toString());
                                    model.setTime(timeEditText.getText().toString());
                                    model.setUser(username);
                                    model.setId(id);
                                    Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                                    WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                                    webServiveAPIs.bookingParking(model).enqueue(new Callback<ParkingBookingModel>() {
                                        @Override
                                        public void onResponse(Call<ParkingBookingModel> call, Response<ParkingBookingModel> response) {
                                            dialog1.dismiss();
                                            ParkingBookingModel parkingBookingModel = response.body();
                                            if (parkingBookingModel != null) {
                                                Toast.makeText(AppUserNearByParkingActivity.this, parkingBookingModel.getStatus(), Toast.LENGTH_SHORT).show();
                                               // if (parkingBookingModel.getStatus().equals("success")) {
                                                    Toast.makeText(AppUserNearByParkingActivity.this,
                                                           parkingBookingModel.getStatus(),
                                                            Toast.LENGTH_LONG).show();
                                                    dialog1.dismiss();
                                               // }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ParkingBookingModel> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                } else {
                                    Toast.makeText(AppUserNearByParkingActivity.this, "Slot not available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void getParkingSpaces(double lat, double longi) {
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        final ParkingLocationModel parkingLocationModel = new ParkingLocationModel();
        parkingLocationModel.setLat(lat + "");
        parkingLocationModel.setLongi(longi + "");
        webServiveAPIs.userGetNearByParkingSpaces(parkingLocationModel).enqueue(new Callback<List<ParkingLocationModel>>() {
            @Override
            public void onResponse(Call<List<ParkingLocationModel>> call, Response<List<ParkingLocationModel>> response) {
                List<ParkingLocationModel> parkingLocationModels = response.body();
                if (parkingLocationModels != null) {
                    plotMarkers(parkingLocationModels);
                }
            }

            @Override
            public void onFailure(Call<List<ParkingLocationModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
