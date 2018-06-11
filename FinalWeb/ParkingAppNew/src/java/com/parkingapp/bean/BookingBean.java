
package com.parkingapp.bean;

/**
 *
 * @author Anoop
 */
public class BookingBean {
     String pa_id = "";
     String user_id = "";
     String booking_time = "";
     String for_time = "";
     String slot_type = "";
     String status_of_parking_area_admin = "";

    public String getPa_id() {
        return pa_id;
    }

    public void setPa_id(String pa_id) {
        this.pa_id = pa_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getFor_time() {
        return for_time;
    }

    public void setFor_time(String for_time) {
        this.for_time = for_time;
    }

    public String getSlot_type() {
        return slot_type;
    }

    public void setSlot_type(String slot_type) {
        this.slot_type = slot_type;
    }

    public String getStatus_of_parking_area_admin() {
        return status_of_parking_area_admin;
    }

    public void setStatus_of_parking_area_admin(String status_of_parking_area_admin) {
        this.status_of_parking_area_admin = status_of_parking_area_admin;
    }
}
