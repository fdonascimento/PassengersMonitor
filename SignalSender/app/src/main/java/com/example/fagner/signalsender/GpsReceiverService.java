package com.example.fagner.signalsender;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by fagner on 28/01/18.
 */

public interface GpsReceiverService {

    @POST("locations/locations")
    Call<LocationInfo> sendLocationInfo(@Body LocationInfo locationInfo);
}
