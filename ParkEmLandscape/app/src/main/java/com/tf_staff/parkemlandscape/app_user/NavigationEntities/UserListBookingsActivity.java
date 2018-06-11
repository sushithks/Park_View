package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.Models.ParkingBookingModel;
import com.tf_staff.parkemlandscape.Models.ParkingBookingModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserListBookingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListFragment mList;
    LocationManager locationManager;
    private MapAdapter mAdapter;
    ProgressDialog progressDialog;
    ParkingBookingModel model;
    String provider = LocationManager.NETWORK_PROVIDER;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_bookings);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.getUserParkingBookings(userName).enqueue(new Callback<List<ParkingBookingModel>>() {
            @Override
            public void onResponse(Call<List<ParkingBookingModel>> call, Response<List<ParkingBookingModel>> response) {
                List<ParkingBookingModel> userParkingHistoryModels = response.body();
                if (userParkingHistoryModels != null) {
                    mAdapter = new MapAdapter(UserListBookingsActivity.this, userParkingHistoryModels);
                    mList = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list);
                    mList.setListAdapter(mAdapter);
                    AbsListView lv = mList.getListView();
                    lv.setRecyclerListener(mRecycleListener);
                }
            }

            @Override
            public void onFailure(Call<List<ParkingBookingModel>> call, Throwable t) {
                t.printStackTrace();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            Intent intent = new Intent(UserListBookingsActivity.this, LoginActivity.class);
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
                        Dialog dialog = new Dialog(UserListBookingsActivity.this);
                        dialog.setTitle("QrCode");
                        dialog.setContentView(R.layout.dialog_qr_code_view);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.qrCodeImageView);
                        Log.e("qr url", appUserInfo.getUrl());
                        Picasso.with(UserListBookingsActivity.this)
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

    private class MapAdapter extends BaseAdapter {

        private final HashSet<MapView> mMaps = new HashSet<MapView>();
        List<ParkingBookingModel> parkingBookingModelList;

        public MapAdapter(Context context, List<ParkingBookingModel> parkingBookingModelList) {
            this.parkingBookingModelList = parkingBookingModelList;
        }


        @Override
        public int getCount() {
            return parkingBookingModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return parkingBookingModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.single_row_user_bookings, null);
                holder = new ViewHolder();
                holder.mapView = (MapView) row.findViewById(R.id.lite_listrow_map);
                holder.checkInTimeTextView = (TextView) row.findViewById(R.id.timeTextView);
                holder.vehicleTypeTextView = (TextView) row.findViewById(R.id.vehicleTypeTextView);
                holder.priceTextView = (TextView) row.findViewById(R.id.priceTextView);
                holder.cancelBookingTextView = (TextView) row.findViewById(R.id.cancelBookingTextView);
                holder.slotTextView = (TextView) row.findViewById(R.id.slotTextView);
                row.setTag(holder);
                holder.initializeMapView();
                mMaps.add(holder.mapView);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            model = (ParkingBookingModel) getItem(position);
            holder.mapView.setTag(model);

            if (holder.map != null) {
                setMapLocation(holder.map, model);
            }
            holder.checkInTimeTextView.setText(model.getTime());
            holder.vehicleTypeTextView.setText(model.getType());
            holder.priceTextView.setText("Price: " + model.getPrice());
            holder.slotTextView.setText("Slot: " + model.getSlot());
            int postion1 = position;

            if (model.getStatus().equals("cancel")) {
                holder.cancelBookingTextView.setTextColor(Color.WHITE);
                holder.cancelBookingTextView.setText("CANCELLED");
                //holder.cancelBookingTextView.setVisibility(View.INVISIBLE);
            } else {
                holder.cancelBookingTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                holder.cancelBookingTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                        ParkingBookingModel mod = parkingBookingModelList.get(position);
                        webServiveAPIs.cancelBooking(mod.getId()).enqueue(new Callback<ParkingBookingModel>() {
                            @Override
                            public void onResponse(Call<ParkingBookingModel> call, Response<ParkingBookingModel> response) {
                                ParkingBookingModel model1 = response.body();
                                if (model1 != null) {
                                    Toast.makeText(UserListBookingsActivity.this, model1.getStatus(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ParkingBookingModel> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                });
            }
            return row;
        }

        public HashSet<MapView> getMaps() {
            return mMaps;
        }
    }

    private static void setMapLocation(GoogleMap map, ParkingBookingModel data) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.getLat(), data.getLongi()), 13f));
        map.addMarker(new MarkerOptions().position(new LatLng(data.getLat(), data.getLongi())));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    class ViewHolder implements OnMapReadyCallback {

        MapView mapView;

        TextView checkInTimeTextView;
        TextView vehicleTypeTextView;
        TextView cancelBookingTextView;
        TextView priceTextView;
        TextView slotTextView;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getApplicationContext());
            map = googleMap;
            ParkingBookingModel data = (ParkingBookingModel) mapView.getTag();
            if (data != null) {
                setMapLocation(map, data);
            }
        }

        public void initializeMapView() {
            if (mapView != null) {
                mapView.onCreate(null);
                mapView.getMapAsync(this);
            }
        }

    }

    private AbsListView.RecyclerListener mRecycleListener = new AbsListView.RecyclerListener() {

        @Override
        public void onMovedToScrapHeap(View view) {
            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder != null && holder.map != null) {
                holder.map.clear();
                holder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        }
    };
}
