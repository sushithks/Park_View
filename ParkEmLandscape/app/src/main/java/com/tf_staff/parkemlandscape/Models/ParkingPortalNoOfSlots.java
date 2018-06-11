package com.tf_staff.parkemlandscape.Models;

/**
 * Created by Kripa on 06-08-2017.
 */

public class ParkingPortalNoOfSlots {
    String noOfSlots = "";
    String typeOfSlots = "";
    String status = "";
    String pAdminUsername = "";

    public String getpAdminUsername() {
        return pAdminUsername;
    }

    public void setpAdminUsername(String pAdminUsername) {
        this.pAdminUsername = pAdminUsername;
    }

    public String getTypeOfSlots() {
        return typeOfSlots;
    }

    public void setTypeOfSlots(String typeOfSlots) {
        this.typeOfSlots = typeOfSlots;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNoOfSlots() {
        return noOfSlots;
    }

    public void setNoOfSlots(String noOfSlots) {
        this.noOfSlots = noOfSlots;
    }

}
