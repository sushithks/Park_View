package com.tf_staff.parkemlandscape.area_admin.NavigationEntities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AreaAdminParkingModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.adapters.AreaAdminParkingHistoryAdapter;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.tf_staff.parkemlandscape.R.id.drawer_layout;

public class AreaAdminParkingHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_admin_parking_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.adminHistoryRecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        String userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        webServiveAPIs.getAdminParkingHistory(userName).enqueue(new Callback<List<AreaAdminParkingModel>>() {
            @Override
            public void onResponse(Call<List<AreaAdminParkingModel>> call, Response<List<AreaAdminParkingModel>> response) {
                List<AreaAdminParkingModel> AreaAdminParkingModels = response.body();
                if (AreaAdminParkingModels != null) {
                    AreaAdminParkingHistoryAdapter adapter = new AreaAdminParkingHistoryAdapter(AreaAdminParkingModels, AreaAdminParkingHistoryActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<AreaAdminParkingModel>> call, Throwable t) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), AreaAdminProfileActivity.class));

        } else if (id == R.id.nav_parking_portal) {
            Intent intent = new Intent(this, AreaAdminParkingPortalActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, AreaAdminParkingHistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.USER_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
