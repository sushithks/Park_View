package com.tf_staff.parkemlandscape.Models;

/**
 * Created by Kripa on 06-08-2017.
 */

public class AreaAdminParkingPortalInfo {
    String timeIn = "";
    String timeOut = "";
    String parkingAmount = "";
    String noOfSlots = "";
    String username = "";
    String status = "";
    String timeFlag = "";
    String padminUsername = "";

    public String getPadminUsername() {
        return padminUsername;
    }

    public void setPadminUsername(String padminUsername) {
        this.padminUsername = padminUsername;
    }

    public String getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getParkingAmount() {
        return parkingAmount;
    }

    public void setParkingAmount(String parkingAmount) {
        this.parkingAmount = parkingAmount;
    }

    public String getNoOfSlots() {
        return noOfSlots;
    }

    public void setNoOfSlots(String noOfSlots) {
        this.noOfSlots = noOfSlots;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
