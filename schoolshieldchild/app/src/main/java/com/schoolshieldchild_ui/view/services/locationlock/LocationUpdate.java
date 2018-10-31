package com.schoolshieldchild_ui.view.services.locationlock;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

/**
 * Created by mb on 28/7/16.
 */
public class LocationUpdate extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static boolean isLockViewVisible = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (SharedPref.getString(MyApplication.STUDENT_ROLE).equalsIgnoreCase("student")) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }
        return START_STICKY;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            if (SharedPref.getString(MyApplication.STUDENT_ROLE).equalsIgnoreCase("student")) {
                Location loc1 = new Location("Location1");
                loc1.setLatitude(location.getLatitude());
                loc1.setLongitude(location.getLongitude());
                Location loc2 = new Location("Location2");
                loc2.setLatitude(Double.parseDouble(SharedPref.getString(MyApplication.STUDENT_LAT)));
                loc2.setLongitude(Double.parseDouble(SharedPref.getString(MyApplication.STUDENT_LON)));
                float distanceInMeters = loc1.distanceTo(loc2);


                if (SharedPref.getString(MyApplication.STUDENT_IS_INSIDE_SCHOOL).equalsIgnoreCase("")) {
                    if (distanceInMeters < 100) {
                        SharedPref.setString(MyApplication.STUDENT_IS_INSIDE_SCHOOL, "YES");
                        //Update in database student enter in school

                        WebServiceResult.saveStuentStatus(SharedPref.getString(MyApplication.STUDENT_ID), "IN");

                    }
                }
                if (SharedPref.getString(MyApplication.STUDENT_IS_INSIDE_SCHOOL).equalsIgnoreCase("YES")) {
                    if (distanceInMeters > 100) {
                        SharedPref.setString(MyApplication.STUDENT_IS_INSIDE_SCHOOL, "NO");
                        //Update in database student out school
                        WebServiceResult.saveStuentStatus(SharedPref.getString(MyApplication.STUDENT_ID), "OUT");
                    }
                }

                if (SharedPref.getString(MyApplication.STUDENT_IS_INSIDE_SCHOOL).equalsIgnoreCase("NO")) {
                    if (distanceInMeters < 100) {
                        SharedPref.setString(MyApplication.STUDENT_IS_INSIDE_SCHOOL, "YES");
                        WebServiceResult.saveStuentStatus(SharedPref.getString(MyApplication.STUDENT_ID), "IN");
                        //Update in database student enter in school
                    }
                }

                if (distanceInMeters < 100) {
                    if (SharedPref.getString(MyApplication.ISLOCATION_LOCKED).equalsIgnoreCase("YES")) {
                        if (isLockViewVisible == false) {
                            startService(new Intent(getApplicationContext(), LocationLockScreenView.class));
                            isLockViewVisible = true;
                        }
                    }
                } else {
                    LocationLockScreenView.stopService();
                    isLockViewVisible = false;
                }

            }

        } catch (NullPointerException e) {

        } catch (NumberFormatException e)

        {

        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
