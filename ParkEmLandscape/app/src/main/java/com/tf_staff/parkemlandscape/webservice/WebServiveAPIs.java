package com.tf_staff.parkemlandscape.webservice;

import com.google.gson.JsonObject;
import com.tf_staff.parkemlandscape.Models.AppUserInfo;
import com.tf_staff.parkemlandscape.Models.AreaAdminHistoryData;
import com.tf_staff.parkemlandscape.Models.AreaAdminInfo;
import com.tf_staff.parkemlandscape.Models.AreaAdminParkingModel;
import com.tf_staff.parkemlandscape.Models.AreaAdminParkingPortalInfo;
import com.tf_staff.parkemlandscape.Models.ParkingBookingModel;
import com.tf_staff.parkemlandscape.Models.ParkingLocationModel;
import com.tf_staff.parkemlandscape.Models.ParkingPortalNoOfSlots;
import com.tf_staff.parkemlandscape.Models.UserParkingHistoryModel;
import com.tf_staff.parkemlandscape.app_user.NavigationEntities.AppUserWalletActivity;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebServiveAPIs {


    @GET("GetParkingInfoServlet")
    public Call<AreaAdminInfo> getAreaAdminInfo(@Query("user_name") String name);

    @POST("AreaAdminRegisterServlet")
    public Call<String> registerAreaAdmin(@Body JSONObject jsonObject);

    @POST("AreaAdminEditServlet")
    public Call<AreaAdminInfo> areaAdminEditProfile(@Body JSONObject jsonObject);

    @POST("AreaAdminParkingInServlet")
    public Call<AreaAdminParkingModel> areaAdminParking(@Body AreaAdminParkingModel object);

    @POST("AreaAdminParkingOutServlet")
    public Call<AreaAdminParkingModel> areaAdminParkingOut(@Body AreaAdminParkingModel object);

    @POST("AreaAdminConfirmPaymentServlet")
    Call<AreaAdminParkingModel> areaAdminConfirmPayment(@Body AreaAdminParkingModel object);

    @GET("AreaAdminHistoryServlet")
    public Call<AreaAdminHistoryData> getParkingHistory(@Query("user_name") String name);

    @GET("GetUserInfoServlet")
    public Call<AppUserInfo> getUserInfo(@Query("user_name") String name);

    @GET("QrCodeServlet")
    public Call<AppUserInfo> getQrCode(@Query("user_name") String name);

    @GET("CancelBookingServlet")
    public Call<ParkingBookingModel> cancelBooking(@Query("id") String id);

    @POST("UserRegisterServlet")
    public Call<String> registerUser(@Body JSONObject jsonObject);

    @POST("UserEditProfileServlet")
    public Call<AppUserInfo> userEditProfile(@Body JSONObject jsonObject);

    @POST("UserBookingServlet")
    public Call<String> userBooking(@Body JSONObject jsonObject);

    @POST("UserGetNearByParkingSpacesServlet")
    public Call<List<ParkingLocationModel>> userGetNearByParkingSpaces(@Body ParkingLocationModel parkingLocationModel);

    @POST("UserBookingServlet")
    public Call<ParkingBookingModel> bookingParking(@Body ParkingBookingModel parkingBookingModel);

    @POST("UserAccountServlet")
    public Call<AppUserInfo> userWalletRecharge(@Body JSONObject jsonObject);

    @POST("UserPostFeedbackServlet")
    public Call<AppUserInfo> userPostFeedback(@Body JSONObject jsonObject);

    @POST("LoginServlet_1")
    public Call<JsonObject> login(@Body JSONObject jsonObject);

    @GET("GetParkingHistoryServlet")
    public Call<List<UserParkingHistoryModel>> getUserParkingHistory(@Query("user_name") String userName);

    @GET("UserGetParkingBookingsServlet")
    public Call<List<ParkingBookingModel>> getUserParkingBookings(@Query("user_name") String userName);

    @GET("GetAdminParkingHistoryServlet")
    public Call<List<AreaAdminParkingModel>> getAdminParkingHistory(@Query("user_name") String userName);

    @GET("FCMUpdateServlet")
    Call<AppUserInfo> fcmUpdateServlet(@Body JSONObject jsonObject);
    @GET("GetUserBalanceDetailsServlet")
    Call<AppUserInfo> getBalanace(@Query("user_name") String userName);
}
