package com.tf_staff.parkemlandscape.Models;

/**
 * Created by Kripa on 06-08-2017.
 */

public class AreaAdminHistoryData {
    String date = "";
    String username = "";
    String checkIn = "";
    String checkOut = "";
    String totalNoOfDetails = "";
    String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getTotalNoOfDetails() {
        return totalNoOfDetails;
    }

    public void setTotalNoOfDetails(String totalNoOfDetails) {
        this.totalNoOfDetails = totalNoOfDetails;
    }
}