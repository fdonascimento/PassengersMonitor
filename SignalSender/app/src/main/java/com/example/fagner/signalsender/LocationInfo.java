package com.example.fagner.signalsender;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by fagner on 28/01/18.
 */

public class LocationInfo {
    private Double latitude;
    private Double longitude;
    @SerializedName("device_identification")
    private String deviceIdentification;
    @SerializedName("send_date")
    private Date sendDate;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDeviceIdentification() {
        return deviceIdentification;
    }

    public void setDeviceIdentification(String deviceIdentification) {
        this.deviceIdentification = deviceIdentification;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
