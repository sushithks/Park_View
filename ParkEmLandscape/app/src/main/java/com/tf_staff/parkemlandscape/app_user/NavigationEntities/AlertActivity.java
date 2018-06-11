package com.tf_staff.parkemlandscape.app_user.NavigationEntities;

import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.R;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AlertActivity extends AppCompatActivity {
    EditText feedbackEditText;
    Button submitFeedBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        TextView alertTextView = (TextView) findViewById(R.id.alertTextView);
        Intent intent = getIntent();
        String emergencyMessage = intent.getStringExtra("message");
        final String id = intent.getStringExtra("id");
        alertTextView.setText(emergencyMessage);
        feedbackEditText = (EditText) findViewById(R.id.feedbackEditText);
        submitFeedBackButton = (Button) findViewById(R.id.submitFeedbackButton);
        if(!emergencyMessage.contains("canceled")) {
           submitFeedBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String feedback = feedbackEditText.getText().toString();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("feed_back", feedback);
                        jsonObject.put("id", id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Retrofit retrofit = new MyRetrofitClient().getRetrofit();
                    WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
                    webServiveAPIs.userPostFeedback(jsonObject).enqueue(new Callback<AppUserInfo>() {
                        @Override
                        public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {
                            AppUserInfo appUserInfo = response.body();
                            if (appUserInfo != null) {
                                Toast.makeText(AlertActivity.this, appUserInfo.getStatus(), Toast.LENGTH_SHORT).show();
                                if (appUserInfo.getStatus().equals("success")) {
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AppUserInfo> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
        } else {
            submitFeedBackButton.setVisibility(View.INVISIBLE);
            feedbackEditText.setVisibility(View.INVISIBLE);
        }

    }

}
