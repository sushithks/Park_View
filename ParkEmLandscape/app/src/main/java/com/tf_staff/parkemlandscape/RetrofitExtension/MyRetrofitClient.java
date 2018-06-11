package com.tf_staff.parkemlandscape.RetrofitExtension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tf_staff.parkemlandscape.utils.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofitClient {
    Retrofit retrofit = null;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.URL)
//                    .baseUrl("http://192.168.225.99:8084/ParkingAppNew/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return  retrofit;
    }
}
