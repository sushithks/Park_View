package com.tf_staff.parkemlandscape.fcm;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.RetrofitExtension.MyRetrofitClient;
import com.tf_staff.parkemlandscape.app_user.NavigationEntities.AlertActivity;
import com.tf_staff.parkemlandscape.utils.AppConstants;
import com.tf_staff.parkemlandscape.webservice.WebServiveAPIs;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyInstanceIdService extends FirebaseInstanceIdService {
    public MyInstanceIdService() {
    }

  public void onTokenRefresh() {
      String fcmId = FirebaseInstanceId.getInstance().getToken();
      Log.e("Refreshed tocken: ", fcmId);
      SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREF, MODE_PRIVATE);
      String username = sharedPreferences.getString(AppConstants.USER_NAME, "");
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString(AppConstants.FCM, fcmId);
      boolean result = editor.commit();

      if (!username.equals("")) {
          JSONObject jsonObject = new JSONObject();
        try {
          jsonObject.put(AppConstants.FCM, fcmId);
          jsonObject.put(AppConstants.USER_NAME, username);
        } catch (JSONException e) {
          e.printStackTrace();
        }
          Retrofit retrofit = new MyRetrofitClient().getRetrofit();
          WebServiveAPIs webServiveAPIs = retrofit.create(WebServiveAPIs.class);
          webServiveAPIs.fcmUpdateServlet(jsonObject).enqueue(new Callback<AppUserInfo>() {
              @Override
              public void onResponse(Call<AppUserInfo> call, Response<AppUserInfo> response) {

              }

              @Override
              public void onFailure(Call<AppUserInfo> call, Throwable t) {
                  t.printStackTrace();
              }
          });
    }

  }
}

