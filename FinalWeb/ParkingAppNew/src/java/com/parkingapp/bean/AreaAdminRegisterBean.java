package com.parkingapp.bean;

/**
 *
 * @author Anoop
 */
public class AreaAdminRegisterBean extends LoginBean{

    String parking_name;
    String username;
    String password;
    String email_id;
    String phone_number;
    String lati;
    String longi;
    String number_for_two_wheeler;
    String two_wheeler_for_1hr;
    String two_wheeler_after_1hr;
    String number_for_four_wheeler;
    String four_wheeler_for_1hr;
    String four_wheeler_after_1hr;
    String userType;

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getNumber_for_two_wheeler() {
        return number_for_two_wheeler;
    }

    public void setNumber_for_two_wheeler(String number_for_two_wheeler) {
        this.number_for_two_wheeler = number_for_two_wheeler;
    }

    public String getTwo_wheeler_for_1hr() {
        return two_wheeler_for_1hr;
    }

    public void setTwo_wheeler_for_1hr(String two_wheeler_for_1hr) {
        this.two_wheeler_for_1hr = two_wheeler_for_1hr;
    }

    public String getTwo_wheeler_after_1hr() {
        return two_wheeler_after_1hr;
    }

    public void setTwo_wheeler_after_1hr(String two_wheeler_after_1hr) {
        this.two_wheeler_after_1hr = two_wheeler_after_1hr;
    }

    public String getNumber_for_four_wheeler() {
        return number_for_four_wheeler;
    }

    public void setNumber_for_four_wheeler(String number_for_four_wheeler) {
        this.number_for_four_wheeler = number_for_four_wheeler;
    }

    public String getFour_wheeler_for_1hr() {
        return four_wheeler_for_1hr;
    }

    public void setFour_wheeler_for_1hr(String four_wheeler_for_1hr) {
        this.four_wheeler_for_1hr = four_wheeler_for_1hr;
    }

    public String getFour_wheeler_after_1hr() {
        return four_wheeler_after_1hr;
    }

    public void setFour_wheeler_after_1hr(String four_wheeler_after_1hr) {
        this.four_wheeler_after_1hr = four_wheeler_after_1hr;
    }
    
}
