package com.tf_staff.parkemlandscape.Models;

/**
 * Created by Kripa on 06-08-2017.
 */

public class AppUserInfo {
    String app_user_name = "";
    String app_user_username = "";
    String app_user_password = "";
    String app_user_email = "";
    String app_user_phoneNumber = "";

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    String balance;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status = "";

    public String getAppUserName() {
        return app_user_name;
    }
    public void setAppUserName(String name) {
        this.app_user_name = name;
    }

    public String getAppUserUsername() {
        return app_user_username;
    }
    public void setAppUserUsername(String username) {
        this.app_user_username = username;
    }

    public String getAppUserPassword() {
        return app_user_password;
    }
    public void setAppUserPassword(String password) {
        this.app_user_password = password;
    }

    public String getAppUserEmail() {
        return app_user_email;
    }
    public void setAppUserEmail(String email) {
        this.app_user_email = email;
    }

    public String getAppUserPhoneNumber() {
        return app_user_phoneNumber;
    }
    public void setAppUserPhoneNumber(String phoneNumber) {
        this.app_user_phoneNumber = phoneNumber;
    }



}
