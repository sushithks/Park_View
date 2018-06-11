package com.tf_staff.parkemlandscape.area_admin.NavigationEntities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.LatLng;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AreaAdminRegistrationActivityLevelTwo extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    Button twoWheelerButton;
    Button fourWheelerButton;
    Button submit;
//    int[] slot_type = {2, 4};


    //dialog box values

    EditText NoOfSlots;
    EditText PriceFor1Hr;
    EditText PriceAfter1Hr;

    Button proceedButton;


    String twoWheelerNoOfSlots = "";
    String twoWheelerPriceFor1Hr = "";
    String twoWheelerPriceAfter1Hr = "";
    String fourWheelerNoOfSlots = "";
    String fourWheelerPriceFor1Hr = "";
    String fourWheelerPriceAfter1Hr = "";

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    PlaceAutocompleteFragment manualSearchAutocompleteFragment;
    double sourceLat;
    double sourceLong;
    EditText sourceEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_level_two_bak);

        twoWheelerButton = (Button) findViewById(R.id.registration_bike_slot_button);
        twoWheelerButton.setOnClickListener(this);
        fourWheelerButton = (Button) findViewById(R.id.registration_car_slot_button);
        fourWheelerButton.setOnClickListener(this);
        submit = (Button) findViewById(R.id.registration_submit_button);
        submit.setOnClickListener(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.admin_edit_profile_map);
        mapFragment.getMapAsync(this);
        manualSearchAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.manual_search_autocomplete_fragment);
        sourceEditText = ((EditText) manualSearchAutocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input));
        sourceEditText.setHint("Enter manually");

        sourceEditText.setTextSize(25f);
        manualSearchAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                final LatLng latLng = place.getLatLng();
//                sourceLat = latLng.latitude;
//                sourceLong = latLng.longitude;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
            }

            @Override
            public void onError(Status status) {
                Log.e("error map: ", status.getStatusMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registration_submit_button) {
            JSONObject jsonObject = new JSONObject();
            Intent intentGetValue = getIntent();
//            Log.e("userType", intentGetValue.getStringExtra("userType"));
//            Log.e("name", intentGetValue.getStringExtra("parkingName"));
//            Log.e("username", intentGetValue.getStringExtra("username"));
//            Log.e("password", intentGetValue.getStringExtra("password"));
//            Log.e("email", intentGetValue.getStringExtra("email"));
//            Log.e("number", intentGetValue.getStringExtra("number"));
            try {
                jsonObject.put("userType", intentGetValue.getStringExtra("userType"));
                jsonObject.put("name", intentGetValue.getStringExtra("parkingName"));
                jsonObject.put("username", intentGetValue.getStringExtra("username"));
                jsonObject.put("password", intentGetValue.getStringExtra("password"));
                jsonObject.put("email", intentGetValue.getStringExtra("email"));
                jsonObject.put("number", intentGetValue.getStringExtra("number"));
                //           jsonObject.put("lattitude_n_longitude", );
                //          jsonObject.put("recharge_amount", recharge_amount);
                jsonObject.put("twoWheelerNoOfSlots", twoWheelerNoOfSlots);
                jsonObject.put("twoWheelerPriceFor1Hr", twoWheelerPriceFor1Hr);
                jsonObject.put("twoWheelerPriceAfter1Hr", twoWheelerPriceAfter1Hr);
                jsonObject.put("fourWheelerNoOfSlots", fourWheelerNoOfSlots);
                jsonObject.put("fourWheelerPriceFor1Hr", fourWheelerPriceFor1Hr);
                jsonObject.put("fourWheelerPriceAfter1Hr", fourWheelerPriceAfter1Hr);
                jsonObject.put("lat", String.valueOf(sourceLat));
                jsonObject.put("long", String.valueOf(sourceLong));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Retrofit retrofit = new MyRetrofitClient().getRetrofit();
            WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
            final ProgressDialog pDialog = new ProgressDialog(AreaAdminRegistrationActivityLevelTwo.this);
            pDialog.setMessage("waiting for response from server");
            pDialog.show();
            webServiveAPIs.registerAreaAdmin(jsonObject).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pDialog.dismiss();
                    Toast.makeText(AreaAdminRegistrationActivityLevelTwo.this, response.body(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AreaAdminRegistrationActivityLevelTwo.this,LoginActivity.class);
                                startActivity(intent);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    pDialog.dismiss();
                    t.printStackTrace();
//                    Toast.makeText(AreaAdminRegistrationActivityLevelTwo.this, "REGISTRATION NOT SUCCESSFULL", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AreaAdminRegistrationActivityLevelTwo.this, CommonRegistrationActivity.class);
//                    startActivity(intent);
                }
            });
//            Intent intent = new Intent(AreaAdminRegistrationActivityLevelTwo.this, LoginActivity.class);
//            startActivity(intent);

        } else if (view.getId() == R.id.registration_bike_slot_button) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_slot_info, null);
            dialogBuilder.setView(dialogView);

            dialogBuilder.setTitle("Edit Bike slot information");
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();

            proceedButton = (Button) dialog.findViewById(R.id.proceed_button_2_submit_slot_values);
            NoOfSlots = (EditText) dialog.findViewById(R.id.no_of_slots_value_via_registration);
            PriceFor1Hr = (EditText) dialog.findViewById(R.id.price_for_1hr_value_via_registration);
            PriceAfter1Hr = (EditText) dialog.findViewById(R.id.price_after_1hr_value_via_registration);

//            twoWheelerNoOfSlots = NoOfSlots.getText().toString();
//            twoWheelerPriceFor1Hr = PriceFor1Hr.getText().toString();
//            twoWheelerPriceAfter1Hr = PriceAfter1Hr.getText().toString();

            // if decline button is clicked, close the custom dialog
            proceedButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    twoWheelerNoOfSlots = NoOfSlots.getText().toString();
                    twoWheelerPriceFor1Hr = PriceFor1Hr.getText().toString();
                    twoWheelerPriceAfter1Hr = PriceAfter1Hr.getText().toString();
                    // Close dialog
                    dialog.dismiss();
                }
            });
        } else if (view.getId() == R.id.registration_car_slot_button) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_slot_info, null);
            dialogBuilder.setView(dialogView);

            dialogBuilder.setTitle("Edit car slot information");
            final AlertDialog dialog = dialogBuilder.create();
            dialog.show();

            proceedButton = (Button) dialog.findViewById(R.id.proceed_button_2_submit_slot_values);
            NoOfSlots = (EditText) dialog.findViewById(R.id.no_of_slots_value_via_registration);
            PriceFor1Hr = (EditText) dialog.findViewById(R.id.price_for_1hr_value_via_registration);
            PriceAfter1Hr = (EditText) dialog.findViewById(R.id.price_after_1hr_value_via_registration);

//            fourWheelerNoOfSlots = NoOfSlots.getText().toString();
//            fourWheelerPriceFor1Hr = PriceFor1Hr.getText().toString();
//            fourWheelerPriceAfter1Hr = PriceAfter1Hr.getText().toString();

            // if decline button is clicked, close the custom dialog
            proceedButton.setOnClickListener(new View.OnClickListener() {
                @Override


                public void onClick(View v) {
                    fourWheelerNoOfSlots = NoOfSlots.getText().toString();
                    fourWheelerPriceFor1Hr = PriceFor1Hr.getText().toString();
                    fourWheelerPriceAfter1Hr = PriceAfter1Hr.getText().toString();
                    // Close dialog
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));\
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            } else {
                mMap.setMyLocationEnabled(true);
            }

        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                final Dialog dialog = new Dialog(AreaAdminRegistrationActivityLevelTwo.this);
                dialog.setContentView(R.layout.dialog_confirm_map_location);
                dialog.setTitle("Confirm");
                dialog.show();
//                android.support.v7.app.AlertDialog.Builder mapBuilder = new android.support.v7.app.AlertDialog.Builder(getApplicationContext());
//                View mapSupportView = getLayoutInflater().inflate(R.layout.dialog_confirm_map_location, null);
////                ImageView dialogImageView = (ImageView) findViewById(R.id.qr_dialog_imageView);

                Button okButton = (Button) dialog.findViewById(R.id.buttonOk);

                Button cancelButton = (Button) dialog.findViewById(R.id.buttonCancel);
//
//                mapBuilder.setView(mapSupportView);
//                final android.support.v7.app.AlertDialog mapSupportDialog = mapBuilder.create();
//                mapSupportDialog.show();
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sourceLat = latLng.latitude;
                        sourceLong = latLng.longitude;
                        dialog.dismiss();
                        Toast.makeText(AreaAdminRegistrationActivityLevelTwo.this,latLng.toString()+"\n"+sourceLat+"\n"+sourceLong , Toast.LENGTH_LONG).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
}
