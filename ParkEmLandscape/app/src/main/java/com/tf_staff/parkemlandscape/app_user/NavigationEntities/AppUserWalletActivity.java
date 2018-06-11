package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
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

public class AppUserWalletActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView recharge, transaction;
    EditText nameOnCard, numberOnCard, rechargeAmount;
    EditText cvvNumberEditText;
    Spinner monthSpinner;
    Spinner yearSpinner;
    TextView balanceTextView;
    String userName;
    String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] years = new String[]{"2017", "2018", "2019", "2020", "2021"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wallet);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        balanceTextView = (TextView) findViewById(R.id.currentBalanceTextView);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        userName = sharedPreferences.getString(AppConstants.USER_NAME, "");
        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
        WebServiveAPIs apIs = retrofit.create(WebServiveAPIs.class);
        apIs.getBalanace(userName).enqueue(new Callback<AppUserInfo>() {
            @Override
            public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                AppUserInfo info = response.body();
                if (info != null) {
                    balanceTextView.setText("Current balance: " + info.getBalance());
                    Toast.makeText(AppUserWalletActivity.this, info.getBalance(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppUserInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AppUserWalletActivity.this, "Error in wallet", Toast.LENGTH_SHORT).show();
            }
        });
        nameOnCard = (EditText) findViewById(R.id.name_on_card_editText);
        numberOnCard = (EditText) findViewById(R.id.card_number_editText);
        rechargeAmount = (EditText) findViewById(R.id.recharge_amount_number_editText);
        monthSpinner = (Spinner) findViewById(R.id.mothSpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        cvvNumberEditText = (EditText) findViewById(R.id.cvvNumberEdittext);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, years);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter2);
        findViewById(R.id.submitRechargeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if (nameOnCard.getText().toString().equals("")) {
                    nameOnCard.setError("Enter name on card");
                    flag = false;
                }
                if (numberOnCard.getText().toString().equals("")) {
                    numberOnCard.setError("Enter card number");
                    flag = false;
                }
                if (rechargeAmount.getText().toString().equals("")) {
                    rechargeAmount.setError("Enter recharge amount");
                    flag = false;
                }
                if (cvvNumberEditText.getText().toString().equals("")) {
                    cvvNumberEditText.setError("Enter cvv number");
                    flag = false;
                }
                if (flag) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("user_name", userName);
                        jsonObject.put("card_name", nameOnCard.getText().toString());
                        jsonObject.put("card_no", numberOnCard.getText().toString());
                        jsonObject.put("amount", rechargeAmount.getText().toString());
                        jsonObject.put("cvv_no", cvvNumberEditText.getText().toString());
                        jsonObject.put("exp_date", monthSpinner.getSelectedItem().toString() + "/" + yearSpinner.getSelectedItem().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                    final WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                    webServiveAPIs.userWalletRecharge(jsonObject).enqueue(new Callback<AppUserInfo>() {
                        @Override
                        public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                            AppUserInfo userInfo = response.body();
                            if (userInfo != null) {
                                Toast.makeText(AppUserWalletActivity.this, userInfo.getStatus(), Toast.LENGTH_SHORT).show();
                                if (userInfo.getStatus().equals("success")) {
                                    clearFileds();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AppUserInfo> call, Throwable t) {
                            Toast.makeText(AppUserWalletActivity.this, "RECHARGE NOT SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

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
            Intent intent = new Intent(AppUserWalletActivity.this, LoginActivity.class);
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
                        Dialog dialog = new Dialog(AppUserWalletActivity.this);
                        dialog.setTitle("QrCode");
                        dialog.setContentView(R.layout.dialog_qr_code_view);
                        ImageView imageView = (ImageView) dialog.findViewById(R.id.qrCodeImageView);
                        Picasso.with(AppUserWalletActivity.this)
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

    void clearFileds() {
        nameOnCard.setText("");
        numberOnCard.setText("");
        rechargeAmount.setText("");
        cvvNumberEditText.setText("");
    }
}
