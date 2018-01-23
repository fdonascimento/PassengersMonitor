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
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private TextView textLatitude;
    private TextView textLongitude;
    private double latitudeGPS;
    private double longitudeGPS;
    private int permissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLatitude = findViewById(R.id.textLatitude);
        textLongitude = findViewById(R.id.textLongitude);
        checkGpsPermission();

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        setLasLocation();
    }

    private void setLasLocation() {
        if(permissionCheck == PackageManager.PERMISSION_GRANTED && isLocationEnabled()) {
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                textLatitude.setText(String.valueOf(location.getLatitude()));
                textLongitude.setText(String.valueOf(location.getLongitude()));
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
