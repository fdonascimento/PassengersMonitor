package com.example.fagner.signalsender;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by fagner on 28/01/18.
 */

public interface GpsReceiverService {

    //The url must end with slash
    @POST("locations/locations/")
    Call<ResponseBody> sendLocationInfo(@Body LocationInfo locationInfo);
}
