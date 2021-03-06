package com.example.fagner.signalsender;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private TextView textLatitude;
    private TextView textLongitude;
    private double latitudeGPS;
    private double longitudeGPS;
    private int permissionCheck;
    private GpsReceiverService gpsReceiver;
    private String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        configureServerClient();
        textLatitude = findViewById(R.id.textLatitude);
        textLongitude = findViewById(R.id.textLongitude);
        checkGpsPermission();

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        setLastLocation();
    }

    private void configureServerClient() {
        Retrofit retrofit = new Retrofit.Builder()
                //The base url must end with slash
                .baseUrl("http://192.168.1.4:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        gpsReceiver = retrofit.create(GpsReceiverService.class);
    }

    private void sendLocation(Double latitude, Double longitude) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLatitude(latitude);
        locationInfo.setLongitude(longitude);
        locationInfo.setSendDate(new Date());
        locationInfo.setDeviceIdentification(androidId);

        Call<ResponseBody> response = gpsReceiver.sendLocationInfo(locationInfo);
        //The request is made only after this method call
        response.enqueue(sendLocationCallback());
    }

    private void setLastLocation() {
        if(permissionCheck == PackageManager.PERMISSION_GRANTED && isLocationEnabled()) {
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    textLatitude.setText(String.valueOf(location.getLatitude()));
                    textLongitude.setText(String.valueOf(location.getLongitude()));
                    sendLocation(location.getLatitude(), location.getLongitude());
                } else {
                    textLatitude.setText("GPS signal not found");
                    textLongitude.setText("GPS signal not found");
                }
            } catch (SecurityException e) {
                showDialogError(e.getMessage());
            }
        }
    }

    private void checkGpsPermission() {
        final int gps_request = 1;
        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, gps_request);
        }
    }

    private void configureGpsLocation() {
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (!isLocationEnabled()) {
                showEnableGpsAlert();
            } else {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 1000, 0.5f, locationListenerGPS);
                } catch (SecurityException e) {
                    showDialogError(e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        configureGpsLocation();
    }

    private void showDialogError(String errorMessage) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Error").setMessage(errorMessage);
        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListenerGPS);
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private Callback<ResponseBody> sendLocationCallback() {
        return new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        };
    }

    private void showEnableGpsAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textLongitude.setText(String.valueOf(longitudeGPS));
                    textLatitude.setText(String.valueOf(latitudeGPS));
                    sendLocation(latitudeGPS, longitudeGPS);
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}
