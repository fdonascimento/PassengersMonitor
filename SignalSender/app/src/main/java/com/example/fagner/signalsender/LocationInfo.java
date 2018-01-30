package com.example.fagner.signalsender;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private String sendDate;

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

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        //The date must be in the format specified in the service
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.sendDate = dateFormat.format(sendDate);
    }
}
