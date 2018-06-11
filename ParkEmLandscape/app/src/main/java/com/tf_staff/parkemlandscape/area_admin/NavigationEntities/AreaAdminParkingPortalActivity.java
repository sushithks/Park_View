package com.tf_staff.parkemlandscape.area_admin.NavigationEntities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.tf_staff.parkemlandscape.LoginActivity;
import com.tf_staff.parkemlandscape.Models.AreaAdminParkingModel;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.tf_staff.parkemlandscape.R.id.drawer_layout;

public class AreaAdminParkingPortalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{



    TextView username;
    Button submit;


    SurfaceView qrReader;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceHolder surfaceHolder;
    Spinner vehicleTypeSpinner;
    Spinner inOutSpinner;
    String mode;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_portal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(this);

        username = (TextView)  findViewById(R.id.customer_username_textView);
        vehicleTypeSpinner = (Spinner) findViewById(R.id.vehicleTypeSpinner);
        ArrayAdapter adapter = new ArrayAdapter(AreaAdminParkingPortalActivity.this, android.R.layout.simple_spinner_item, new String[]{"car", "bike"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(adapter);
        inOutSpinner = (Spinner) findViewById(R.id.inOutTypeSpinner);

        ArrayAdapter adapter1 = new ArrayAdapter(AreaAdminParkingPortalActivity.this, android.R.layout.simple_spinner_item, new String[]{"in", "out"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inOutSpinner.setAdapter(adapter1);

        qrReader = (SurfaceView) findViewById(R.id.qrReader);
        qrReader.setZOrderMediaOverlay(true);
        surfaceHolder = qrReader.getHolder();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        if (!barcodeDetector.isOperational()) {
            Toast.makeText(getApplicationContext(), "Sorry, coudn't setup the detecter", Toast.LENGTH_SHORT).show();
            this.finish();
        }

//        cameraSource = new CameraSource.Builder(this, barcodeDetector)
//                .setFacing(cameraSource.CAMERA_FACING_BACK)
//                .setRequestedFps(24)
//                .setAutoFocusEnabled(true)
//                .setRequestedPreviewSize(1280, 720)
//                .build();

//        qrReader.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder surfaceHolder) {
//
//                try {
//                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                        cameraSource.start(qrReader.getHolder());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//            }
//        });
////        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//            @Override
//            public void release() {
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections detections) {
//                final SparseArray<Barcode> barcodeSparseArray= detections.getDetectedItems();
//                if (barcodeSparseArray.size() > 0){
//                    final Barcode barcode = (Barcode) barcodeSparseArray.valueAt(0);
//                    username.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            username.setText(barcode.displayValue);
//                        }
//                    });
//                }
//            }
//        });

    }

    @Override
    protected void onStart() {

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(cameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1280, 720)
                .build();


        qrReader.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(qrReader.getHolder());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
//        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections detections) {
                final SparseArray<Barcode> barcodeSparseArray= detections.getDetectedItems();
                if (barcodeSparseArray.size() > 0){
                    final Barcode barcode = (Barcode) barcodeSparseArray.valueAt(0);
                    username.post(new Runnable() {
                        @Override
                        public void run() {
                            username.setText(barcode.displayValue);
                        }
                    });
                }
            }
        });
        super.onStart();
    }

    @Override
    protected void onPause() {

        cameraSource.stop();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
//            super.onBackPressed();
            Intent intent = new Intent(this, AreaAdminProfileActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, AreaAdminProfileActivity.class);
            startActivity(intent);
//        }else if (id == R.id.nav_qr) {
//            Intent intent = new Intent(this, AreaAdminQR_Activity.class);
//            startActivity(intent);

        } else if (id == R.id.nav_parking_portal) {
            Intent intent = new Intent(this, AreaAdminParkingPortalActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, AreaAdminParkingHistoryActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_log_out) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
//        else if (id == R.id.nav_change_password) {
//            Intent intent = new Intent(this, AreaAdminProfileActivity.class);
//            startActivity(intent);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == submit.getId()){
            if(inOutSpinner.getSelectedItem().toString().equals("in")) {
                String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
                String userName = username.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
                String pAdmin = sharedPreferences.getString(AppConstants.USER_NAME, "");
                Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                final AreaAdminParkingModel areaAdminParkingModel = new AreaAdminParkingModel();
                areaAdminParkingModel.setpAdmin(pAdmin);
                areaAdminParkingModel.setUserName(userName);
                areaAdminParkingModel.setVehicleType(vehicleType);
                webServiveAPIs.areaAdminParking(areaAdminParkingModel).enqueue(new Callback<AreaAdminParkingModel>() {
                    @Override
                    public void onResponse(Call<AreaAdminParkingModel> call, Response<AreaAdminParkingModel> response) {
                        AreaAdminParkingModel areaAdminParkingModel1 = response.body();
                        if (areaAdminParkingModel1 != null) {
                            String responseMsg = areaAdminParkingModel1.getResponse();
                            if (responseMsg.equals("success")) {
                                Toast.makeText(AreaAdminParkingPortalActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AreaAdminParkingPortalActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AreaAdminParkingModel> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(AreaAdminParkingPortalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
                String userName = username.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
                String pAdmin = sharedPreferences.getString(AppConstants.USER_NAME, "");
                Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                final AreaAdminParkingModel areaAdminParkingModel = new AreaAdminParkingModel();
                areaAdminParkingModel.setpAdmin(pAdmin);
                areaAdminParkingModel.setUserName(userName);
                areaAdminParkingModel.setVehicleType(vehicleType);
                webServiveAPIs.areaAdminParkingOut(areaAdminParkingModel).enqueue(new Callback<AreaAdminParkingModel>() {
                    @Override
                    public void onResponse(Call<AreaAdminParkingModel> call, Response<AreaAdminParkingModel> response) {
                        final AreaAdminParkingModel areaAdminParkingModel1 = response.body();
                        if (areaAdminParkingModel1 != null) {
                            String responseMsg = areaAdminParkingModel1.getResponse();
                            if (responseMsg.equals("success")) {
                                String cash = areaAdminParkingModel1.getCash();
                                final Dialog dialog = new Dialog(AreaAdminParkingPortalActivity.this);
                                dialog.setContentView(R.layout.dialog_payment_confirm_dialog);
                                TextView textView = (TextView) dialog.findViewById(R.id.cashTextView);
                                textView.setText("Cash: " + cash);
                                TextView timeTextView = (TextView) dialog.findViewById(R.id.timeTextView);
                                timeTextView.setText("Time: " + areaAdminParkingModel1.getTotalTime());
                                dialog.show();
                                final RadioButton cashRadioButton =
                                        (RadioButton) dialog.findViewById(R.id.cashRadioButton);
                                final RadioButton onlineRadioButton =
                                        (RadioButton) dialog.findViewById(R.id.onlineRadioButton);

                                dialog.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (cashRadioButton.isChecked()) {
                                            onlineRadioButton.setChecked(false);
                                            mode = "cash";
                                        } else if (onlineRadioButton.isChecked()) {
                                            cashRadioButton.setChecked(false);
                                            mode = "online";
                                        }
                                        Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                                        WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                                        AreaAdminParkingModel areaAdminParkingModel = new AreaAdminParkingModel();
                                        areaAdminParkingModel.setPaymentMode(mode);
                                        areaAdminParkingModel.setId(areaAdminParkingModel1.getId());
                                        webServiveAPIs.areaAdminConfirmPayment(areaAdminParkingModel).enqueue(new Callback<AreaAdminParkingModel>() {
                                            @Override
                                            public void onResponse(Call<AreaAdminParkingModel> call, Response<AreaAdminParkingModel> response) {
                                                AreaAdminParkingModel model = response.body();
                                                if (model != null) {
                                                    dialog.dismiss();
                                                    Toast.makeText(AreaAdminParkingPortalActivity.this, model.getResponse(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<AreaAdminParkingModel> call, Throwable t) {
                                                t.printStackTrace();
                                                Toast.makeText(AreaAdminParkingPortalActivity.this, "error", Toast.LENGTH_SHORT).show();
                                                ;
                                            }
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(AreaAdminParkingPortalActivity.this, responseMsg, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AreaAdminParkingModel> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(AreaAdminParkingPortalActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
