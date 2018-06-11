package com.tf_staff.parkemlandscape.Models;

import com.google.gson.annotations.SerializedName;



public class AreaAdminParkingModel {

    String response;
    String id;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    @SerializedName("feed_back")
    String feedback;

    @SerializedName("total_time")
    String totalTime;

    String cash;

    @SerializedName("payment_mode")
    String paymentMode;

    @SerializedName("user_name")
    String userName;

    @SerializedName("p_admin")
    String pAdmin;

    @SerializedName("vehicle_type")
    String vehicleType;

    @SerializedName("check_in_time")
    String checkInTime;

    @SerializedName("check_out_time")
    String checkOutTime;
    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getpAdmin() {
        return pAdmin;
    }

    public void setpAdmin(String pAdmin) {
        this.pAdmin = pAdmin;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

}
