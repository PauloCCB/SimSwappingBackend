package com.simswapping.model;

import java.io.Serializable;

public class BodyLogin implements Serializable {
    private String acc;
    private String pin;
    private double latitude;
    private double longitude;

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

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String passcode) {
        this.pin = passcode;
    }
}