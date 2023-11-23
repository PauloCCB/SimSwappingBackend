package com.simswapping.model;

import java.io.Serializable;

public class BodyLogin implements Serializable {
    private String cc;
    private String pin;
    private double latitude;
    private double longitude;

    private String imei;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String passcode) {
        this.pin = passcode;
    }
}