package com.simswapping.model;

import java.io.Serializable;

public class BodyLogin implements Serializable {
    private String acc;
    private String pin;
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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