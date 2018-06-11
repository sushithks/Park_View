package com.tf_staff.parkemlandscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.area_admin.NavigationEntities.AreaAdminRegistrationActivityLevelTwo;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommonRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView proceed = null;
    TextView reset = null;

    EditText name = null;
    EditText username = null;
    EditText password = null;
    EditText email = null;
    EditText number = null;


    //area admin fields
    EditText parkingName = null;
    EditText areaAdminUsername = null;
    EditText areaAdminPassword = null;
    EditText areaAdminEmail = null;
    EditText areaAdminNumber = null;

    String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String mobilePattern = "[0-9]{10}";
    Button admin_proceed = null;

    WebServiveAPIs webServiveAPIs = null;
    Retrofit retrofit = null;
    String user = "";


    Spinner spinner_for_user_type;
    ArrayAdapter<CharSequence> spinnerAdapter;
    View mview;
    LinearLayout userTypeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_registration);

        userTypeLayout = (LinearLayout) findViewById(R.id.user_type_based_linearLayout);
        spinner_for_user_type = (Spinner) findViewById(R.id.user_type_spinner);
        retrofit = new MyRetrofitClient().getRetrofit();
        webServiveAPIs = retrofit.create(WebServiveAPIs.class);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.user_type, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_for_user_type.setAdapter(spinnerAdapter);
        spinner_for_user_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater layoutInflater = getLayoutInflater();
                if (position == 0) {
                    userTypeLayout.removeAllViews();
                    mview = layoutInflater.inflate(R.layout.activity_registration_level_one, userTypeLayout, true);

                    //AREA ADMIN REGISTRATION
                    parkingName = (EditText) findViewById(R.id.register_parking_name_editText);
                    areaAdminUsername = (EditText) findViewById(R.id.register_username_editText);
                    areaAdminPassword = (EditText) findViewById(R.id.register_password_editText);
                    areaAdminEmail = (EditText) findViewById(R.id.register_emailid_editText);
                    areaAdminNumber = (EditText) findViewById(R.id.register_ph_editText);

                    admin_proceed = (Button) findViewById(R.id.register_proceed_button);
                    admin_proceed.setOnClickListener(CommonRegistrationActivity.this);
                } else {
                    userTypeLayout.removeAllViews();
                    mview = layoutInflater.inflate(R.layout.activity_user_registration, userTypeLayout, true);

                    //PARK EM USER REGISTRATION
                    name = (EditText) findViewById(R.id.user_registration_name_editText);
                    username = (EditText) findViewById(R.id.user_registration_username_editText);
                    password = (EditText) findViewById(R.id.user_registration_password_editText);
                    email = (EditText) findViewById(R.id.user_registration_email_editText);
                    number = (EditText) findViewById(R.id.user_registration_phone_number_editText);

                    proceed = (TextView) findViewById(R.id.user_registration_proceed_TexttView);
                    proceed.setOnClickListener(CommonRegistrationActivity.this);
                    reset = (TextView) findViewById(R.id.user_registration_reset_TexttView);
                    reset.setOnClickListener(CommonRegistrationActivity.this);

                }
                user = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_proceed_button) {
            String emailstr = areaAdminEmail.getText().toString().trim();
            boolean flag = true;
            if (parkingName.getText().toString().equals("")) {
                parkingName.setError("Enter parking name");
                flag = false;
            }
            if (areaAdminUsername.getText().toString().equals("")) {
                areaAdminUsername.setError("Enter username");
                flag = false;
            }
            if (areaAdminNumber.getText().toString().equals("")) {
                areaAdminNumber.setError("Enter mobile number");
                flag = false;
            }

            if (areaAdminPassword.getText().toString().equals("")) {
                areaAdminPassword.setError("Enter password");
                flag = false;
            }
            if (areaAdminEmail.getText().toString().equals("")) {
                areaAdminEmail.setError("Enter Email id");
                flag = false;
            }

            if(parkingName.length() == 0 || areaAdminUsername.length() == 0 || areaAdminNumber.length() == 0
                    || areaAdminPassword.length() == 0 || areaAdminEmail.length() == 0){
                Toast.makeText(getApplicationContext(), "Please fill empty fields",
                        Toast.LENGTH_SHORT).show();
                flag = false;
            }
           else if (!emailstr.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Invalid email address",
                        Toast.LENGTH_SHORT).show();
                flag = false;
            }
           else if(!areaAdminNumber.getText().toString().matches(mobilePattern)){
                Toast.makeText(getApplicationContext(),"Invalid mobile number",Toast.LENGTH_SHORT).show();
                flag = false;
            }




            if (flag == true) {

                Intent intent = new Intent(CommonRegistrationActivity.this, AreaAdminRegistrationActivityLevelTwo.class);
                intent.putExtra("key", "value");
                intent.putExtra("userType", user);
                //intent.putExtra("name", name.getText().toString());
                intent.putExtra("parkingName", parkingName.getText().toString());
                intent.putExtra("username", areaAdminUsername.getText().toString());
                intent.putExtra("number", areaAdminNumber.getText().toString());
                intent.putExtra("password", areaAdminPassword.getText().toString());
                intent.putExtra("email", areaAdminEmail.getText().toString());
                startActivity(intent);
            }
        } else if (view.getId() == R.id.user_registration_proceed_TexttView) {
            boolean flag = true;
            String emstr=email.getText().toString().trim();
            if (name.getText().toString().equals("")) {
                name.setError("Enter  name");
                flag = false;
            }
            if (username.getText().toString().equals("")) {
                username.setError("Enter username");
                flag = false;
            }
            if (password.getText().toString().equals("")) {
                password.setError("Enter password");
                flag = false;
            }
            if (email.getText().toString().equals("")) {
                email.setError("Enter email id");
                flag = false;
            }

            if (number.getText().toString().equals("")) {
                number.setError("Enter mobile number");
                flag = false;
            }
            if(name.length() == 0 || username.length() == 0 || password.length() == 0
                    || email.length() == 0 || number.length() == 0){
                Toast.makeText(getApplicationContext(), "Please fill empty fields",
                        Toast.LENGTH_SHORT).show();
                flag = false;
            }
           else if (!emstr.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Invalid email address",
                        Toast.LENGTH_SHORT).show();
                flag = false;
            }
            else if(!number.getText().toString().matches(mobilePattern)){
                Toast.makeText(getApplicationContext(),"Invalid mobile number",Toast.LENGTH_SHORT).show();
                flag = false;
            }
            if(flag==true) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userType", user);
                    jsonObject.put("name", name.getText().toString());
                    jsonObject.put("username", username.getText().toString());
                    jsonObject.put("password", password.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("number", number.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final ProgressDialog pDialog = new ProgressDialog(CommonRegistrationActivity.this);
                pDialog.setMessage("waiting for response from server");
                pDialog.show();
                webServiveAPIs.registerUser(jsonObject).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        pDialog.dismiss();
                        Log.e("response", response.message());
//                    String msg = response.body();
//                    Toast.makeText(CommonRegistrationActivity.this, "REGISTRATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CommonRegistrationActivity.this, response.body(), Toast.LENGTH_SHORT).show();
//                    if (msg.trim().equals("success")) {
//                        Toast.makeText(CommonRegistrationActivity.this, "REGISTRATION SUCCESSFULL", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CommonRegistrationActivity.this, response.body() + "", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

//                    }
                    }


                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        pDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(CommonRegistrationActivity.this, "REGISTRATION NOT SUCCESSFULL", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();


        } else if (view.getId() == R.id.user_registration_reset_TexttView) {
            clear(name, username, password, email, number);
        }
    }

    private void clear(EditText... editText) {
        for (EditText txt : editText) {
            txt.setText("");
        }

    }
}
