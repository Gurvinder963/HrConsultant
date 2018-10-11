package com.hrconsultant.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.hrconsultant.HRApp;

import com.hrconsultant.request.LocationBody;
import com.hrconsultant.request.ReportBody;
import com.hrconsultant.response.LocationResponse;
import com.hrconsultant.response.ReportResponse;
import com.hrconsultant.views.CustomProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLocation {
    private LocationRequest mLocationRequest;
    private static final int REQUEST_CHECK_SETTINGS = 21213;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
   Context mContext;
    public GetLocation(Context context){
        mContext=context;

    }

    public void build(){
        createLocationRequest();
        checkLocationSettings();
    }
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(false);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Objects.requireNonNull(mContext)).checkLocationSettings(builder.build());

       result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
           @Override
           public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
              try {


                  LocationSettingsResponse response = task.getResult(ApiException.class);
                  // All location settings are satisfied. The client can initialize location requests here.
                  getLocation();
              }
              catch (ApiException exception){

                  switch (exception.getStatusCode()) {

                      case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                          // Location settings are not satisfied. But could be fixed by showing the
                          // user a dialog.
                          // Cast to a resolvable exception.

                          break;
                      case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                          // Location settings are not satisfied. However, we have no way to fix the
                          // settings so we won't show the dialog.
                          break;
                  }
Log.d("djhfj","djfhdj");
              }
           }
       });


    }


    @SuppressLint("MissingPermission")
    private void getLocation() {

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(mContext));

        Task<Location> location = mFusedLocationClient.getLastLocation();
        location.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Toast.makeText(LocationDetectFragment.this.getActivity(), "LAT: " + location.getLatitude() + " LONG: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    updateCityAndPincode(location.getLatitude(), location.getLongitude());
                }
            }
        });







    }



    private void updateCityAndPincode(double latitude, double longitude) {
        try {
            Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String add1 = "" + addresses.get(0).getAddressLine(0);
                String City = "" + addresses.get(0).getLocality();
                String State = "" + addresses.get(0).getAdminArea();
                String Country = "" + addresses.get(0).getCountryName();
                String postal = "" + addresses.get(0).getPostalCode();

                if (City != null && City.length() > 0 && State != null && State.length() > 0 && Country != null && Country.length() > 0) {
                    saveLocation(add1,latitude,longitude);
                }


            }

        } catch (Exception e) {
            Log.d("djhfj","djfhdj");

        }

    }

    private void saveLocation(String s, double latitude, double longitude) {

        if (NetworkFactory.getInstance().isNetworkAvailable(mContext)) {

            LocationBody body = new LocationBody();
            body.setUserId(Integer.parseInt(HRApp.getPreferenceManager().getLoginId()));
            body.setAddress(s);
            body.setLatitude(latitude+"");
            body.setLongitude(longitude+"");
            HRApp.getInstance().getRestClient().getUserCredentialService().saveLocation(body).enqueue(new Callback<LocationResponse>() {
                @Override
                public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {

                        if (response != null) {


                        }
                    }



                @Override
                public void onFailure(Call<LocationResponse> call, Throwable t) {


                }
            });
        } else {

        }
    }

}
