package com.tf_staff.parkemlandscape.Models;

import com.google.gson.annotations.SerializedName;

public class UserParkingHistoryModel {
    @SerializedName("check_in_time")
    private String checkInTime;

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getChekoutTime() {
        return chekoutTime;
    }

    public void setChekoutTime(String chekoutTime) {
        this.chekoutTime = chekoutTime;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @SerializedName("check_out_time")
    private String chekoutTime;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    private double lat;
    private double longi;
    private String date;
    private String cash;
    @SerializedName("payment_mode")
    private String paymentMode;


}
