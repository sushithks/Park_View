package com.tf_staff.parkemlandscape.Models;

import com.google.gson.annotations.SerializedName;

public class ParkingLocationModel {

    String name ;
    String lat;
    String distance;
    String longi;
    @SerializedName("motor_bikes_slots")
    String motorBikeSlots;
    @SerializedName("car_slots")
    String carSlots;
    @SerializedName("minimum_charge_bike")
    String minimumChargeBike;
    @SerializedName("minimum_charge_car")
    String minimumChargeCar;
    @SerializedName("bike_after_one_hr")
    String bikeAfterOneHr;
    @SerializedName("car_after_one_hr")
    String carAfterOneHr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
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

    public String getMotorBikeSlots() {
        return motorBikeSlots;
    }

    public void setMotorBikeSlots(String motorBikeSlots) {
        this.motorBikeSlots = motorBikeSlots;
    }

    public String getCarSlots() {
        return carSlots;
    }

    public void setCarSlots(String carSlots) {
        this.carSlots = carSlots;
    }

    public String getMinimumChargeBike() {
        return minimumChargeBike;
    }

    public void setMinimumChargeBike(String minimumChargeBike) {
        this.minimumChargeBike = minimumChargeBike;
    }

    public String getMinimumChargeCar() {
        return minimumChargeCar;
    }

    public void setMinimumChargeCar(String minimumChargeCar) {
        this.minimumChargeCar = minimumChargeCar;
    }

    public String getBikeAfterOneHr() {
        return bikeAfterOneHr;
    }

    public void setBikeAfterOneHr(String bikeAfterOneHr) {
        this.bikeAfterOneHr = bikeAfterOneHr;
    }

    public String getCarAfterOneHr() {
        return carAfterOneHr;
    }

    public void setCarAfterOneHr(String carAfterOneHr) {
        this.carAfterOneHr = carAfterOneHr;
    }

}
