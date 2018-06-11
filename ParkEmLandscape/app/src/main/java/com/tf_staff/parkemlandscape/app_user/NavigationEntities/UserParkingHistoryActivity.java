package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.Models.UserParkingHistoryModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserParkingHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListFragment mList;

    private MapAdapter mAdapter;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_history);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.getUserParkingHistory(userName).enqueue(new Callback<List<UserParkingHistoryModel>>() {
            @Override
            public void onResponse(Call<List<UserParkingHistoryModel>> call, Response<List<UserParkingHistoryModel>> response) {
                List<UserParkingHistoryModel> userParkingHistoryModels = response.body();
                if (userParkingHistoryModels != null) {
                    mAdapter = new MapAdapter(UserParkingHistoryActivity.this, userParkingHistoryModels);
                    mList = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list);
                    mList.setListAdapter(mAdapter);
                    AbsListView lv = mList.getListView();
                    lv.setRecyclerListener(mRecycleListener);
                }
            }

            @Override
            public void onFailure(Call<List<UserParkingHistoryModel>> call, Throwable t) {
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

    private class MapAdapter extends BaseAdapter {

        private final HashSet<MapView> mMaps = new HashSet<MapView>();
        List<UserParkingHistoryModel> userParkingHistoryModelList;
        public MapAdapter(Context context, List<UserParkingHistoryModel> userParkingHistoryModelList) {
            this.userParkingHistoryModelList = userParkingHistoryModelList;
        }


        @Override
        public int getCount() {
            return userParkingHistoryModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return userParkingHistoryModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;
            if (row == null) {
                row = getLayoutInflater().inflate(R.layout.single_row_user_history, null);
                holder = new ViewHolder();
                holder.mapView = (MapView) row.findViewById(R.id.lite_listrow_map);
                holder.checkInTimeTextView = (TextView) row.findViewById(R.id.checkInTextView);
                holder.checkOutTimeTextView = (TextView) row.findViewById(R.id.checkOutTextView);
                holder.cashTextView = (TextView) row.findViewById(R.id.cashTextView);
                holder.paymentModeTextView = (TextView) row.findViewById(R.id.paymentModeTextView);
                row.setTag(holder);
                holder.initializeMapView();
                mMaps.add(holder.mapView);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            UserParkingHistoryModel item = (UserParkingHistoryModel) getItem(position);
            holder.mapView.setTag(item);

            if (holder.map != null) {
                setMapLocation(holder.map, item);
            }
            holder.checkInTimeTextView.setText(item.getCheckInTime());
            holder.checkOutTimeTextView.setText(item.getChekoutTime());
            holder.cashTextView.setText(item.getCash());
            holder.paymentModeTextView.setText(item.getPaymentMode());
            return row;
        }

        public HashSet<MapView> getMaps() {
            return mMaps;
        }
    }

    private static void setMapLocation(GoogleMap map, UserParkingHistoryModel data) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.getLat(), data.getLongi()), 13f));
        map.addMarker(new MarkerOptions().position(new LatLng(data.getLat(), data.getLongi())));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
            Intent intent = new Intent(UserParkingHistoryActivity.this, LoginActivity.class);
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
                        Dialog dialog = new Dialog(UserParkingHistoryActivity.this);
                        dialog.setTitle("QrCode");
                        dialog.setContentView(R.layout.dialog_qr_code_view);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.qrCodeImageView);
                        Picasso.with(UserParkingHistoryActivity.this)
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


    class ViewHolder implements OnMapReadyCallback {

        MapView mapView;

        TextView checkInTimeTextView;
        TextView checkOutTimeTextView;
        TextView cashTextView;
        TextView paymentModeTextView;

        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getApplicationContext());
            map = googleMap;
            UserParkingHistoryModel data = (UserParkingHistoryModel) mapView.getTag();
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
