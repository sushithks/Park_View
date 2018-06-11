package com.tf_staff.parkemlandscape.Models;

/**
 * Created by Kripa on 06-08-2017.
 */

public class AreaAdminInfo {
    String area_admin_parking_name = "";
    String area_admin_username = "";
    String area_admin_password = "";
    String area_admin_email = "";
    String area_admin_phoneNumber = "";
    String twoWheelerNoOfSlots = "";
    String twoWheelerPriceFor1Hr = "";
    String twoWheelerPriceAfter1Hr = "";
    String fourWheelerNoOfSlots = "";
    String fourWheelerPriceFor1Hr = "";
    String fourWheelerPriceAfter1Hr = "";
    String status = "";

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    String lat;
    String longi;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getTwoWheelerNoOfSlots() {
        return twoWheelerNoOfSlots;
    }
    public void setTwoWheelerNoOfSlots(String twoWheelerNoOfSlots) {
        this.twoWheelerNoOfSlots = twoWheelerNoOfSlots;
    }

    public String getTwoWheelerPriceFor1Hr() {
        return twoWheelerPriceFor1Hr;
    }
    public void setTwoWheelerPriceFor1Hr(String twoWheelerPriceFor1Hr) {
        this.twoWheelerPriceFor1Hr = twoWheelerPriceFor1Hr;
    }

    public String getTwoWheelerPriceAfter1Hr() {
        return twoWheelerPriceAfter1Hr;
    }
    public void setTwoWheelerPriceAfter1Hr(String twoWheelerPriceAfter1Hr) {
        this.twoWheelerPriceAfter1Hr = twoWheelerPriceAfter1Hr;
    }

    public String getFourWheelerNoOfSlots() {
        return fourWheelerNoOfSlots;
    }
    public void setFourWheelerNoOfSlots(String fourWheelerNoOfSlots) {
        this.fourWheelerNoOfSlots = fourWheelerNoOfSlots;
    }

    public String getFourWheelerPriceFor1Hr() {
        return fourWheelerPriceFor1Hr;
    }
    public void setFourWheelerPriceFor1Hr(String fourWheelerPriceFor1Hr) {
        this.fourWheelerPriceFor1Hr = fourWheelerPriceFor1Hr;
    }

    public String getFourWheelerPriceAfter1Hr() {
        return fourWheelerPriceAfter1Hr;
    }
    public void setFourWheelerPriceAfter1Hr(String fourWheelerPriceAfter1Hr) {
        this.fourWheelerPriceAfter1Hr = fourWheelerPriceAfter1Hr;
    }

    public String getAreaAdminName() {
        return area_admin_parking_name;
    }
    public void setAreaAdminName(String name) {
        this.area_admin_parking_name = name;
    }

    public String getAreaAdminUsername() {
        return area_admin_username;
    }
    public void setAreaAdminUsername(String username) {
        this.area_admin_username = username;
    }

    public String getAreaAdminPassword() {
        return area_admin_password;
    }
    public void setAreaAdminPassword(String password) {
        this.area_admin_password = password;
    }

    public String getAreaAdminEmail() {
        return area_admin_email;
    }
    public void setAreaAdminEmail(String email) {
        this.area_admin_email = email;
    }

    public String getAreaAdminPhoneNumber() {
        return area_admin_phoneNumber;
    }
    public void setAreaAdminPhoneNumber(String phoneNumber) {
        this.area_admin_phoneNumber = phoneNumber;
    }
}
