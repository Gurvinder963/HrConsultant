package com.hrconsultant;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Objects;

import com.hrconsultant.Fragment.CompaniesListFragment;
import com.hrconsultant.Fragment.HomeFragment;
import com.hrconsultant.R;
import com.hrconsultant.utils.TimeChangeReceiver;
import com.hrconsultant.utils.Utils;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener,NavigationView.OnNavigationItemSelectedListener{

   // private CustomProgressDialog customProgressDialog;
    public Toolbar mToolbar;
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private TextView tvToolbarTitle;
    private int selectedItem;
    private boolean mChangeFragment;
    private static final String ARG_PARAM1 = "param1";
    private static final int REQUEST_CHECK_SETTINGS = 21213;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
   // FragmentDetectLocationBinding locationDetectBinding;
    private LocationRequest mLocationRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupNavigationView();




        CompaniesListFragment fragment = CompaniesListFragment.newInstance();
        Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment, CompaniesListFragment.class.getSimpleName(), true, R.id.fragmentContainer);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkForPermissions();
            }
        }, 500);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Intent intent = new Intent(MainActivity.this, TimeChangeReceiver.class);
                        boolean alarmUp = (android.app.PendingIntent.getBroadcast(MainActivity.this, 121, intent, android.app.PendingIntent.FLAG_NO_CREATE) != null);
                        if (!alarmUp) {


                            android.app.AlarmManager alarmMgr = (android.app.AlarmManager) getSystemService(android.content.Context.ALARM_SERVICE);
                            android.app.PendingIntent alarmIntent = android.app.PendingIntent.getBroadcast(MainActivity.this, 121, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);

                            java.util.Calendar calendar = java.util.Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
                            calendar.set(java.util.Calendar.MINUTE, 2);


                            alarmMgr.setRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 2 /** 60 * 24*/, alarmIntent);
                        } else {
                            // Utils.printLog("alarm", "already set");
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                       // Toast.makeText(getActivity(), "You must turn on the location to be able to search the location automatically.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false); // disables default title
        setupToolbar();
        setupNavigationView();


    }
    public void setToolBarTitle(String toolBarTitle) {
        tvToolbarTitle.setText(toolBarTitle);
        assert getSupportActionBar() != null;
        getSupportActionBar().show();
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS Settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.tbTitle);

        ImageView ivInfo = (ImageView) findViewById(R.id.ivInfo);
        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvToolbarTitle.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        tvToolbarTitle.setText("Diti Field Report");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setTitle("");
        mToolbar.setSubtitle("");

    }
    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        View headerView = navigationView.getHeaderView(0);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (mChangeFragment) {
                    mChangeFragment = false;
                    switch (selectedItem) {
                        case R.id.menu_home:
                           // HomeFragment fragment = HomeFragment.newInstance();
                           // Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment, SearchFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                            break;
                        case R.id.menu_add_patient:
                          //  AllFamliesFragment fragment2 = AllFamliesFragment.newInstance();
                          //  Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment2, AllFamliesFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                            break;
                        case R.id.menu_search_patient:
                           // SearchFragment fragment1 = SearchFragment.newInstance();
                           // Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment1, SearchFragment.class.getSimpleName(), true, R.id.fragmentContainer);

                            break;



                        case R.id.menu_logOut:

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                            break;


                    }
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
               // Utils.hideSoftKeyboard(MainActivity.this);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getFragmentManager().addOnBackStackChangedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem drawerItem) {
        drawerItem.setChecked(!drawerItem.isChecked()); // set selected state in navigation item
        selectedItem = drawerItem.getItemId();
        mDrawerLayout.closeDrawers(); // Closes drawer as soon as item is clicked
        mChangeFragment = true;
        return true;
    }


    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count == 1) //1 == HomeFragment
            {
                finish();
            } else {
                super.onBackPressed();

            }
        }
    }


    private void checkForPermissions() {
        Dexter.withActivity(MainActivity.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                //Toast.makeText(getActivity(), "Searching for location", Toast.LENGTH_SHORT).show();
                createLocationRequest();
                checkLocationSettings();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
               // Toast.makeText(getActivity(), response.getPermissionName(), Toast.LENGTH_SHORT).show();
                if (response.isPermanentlyDenied()) {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(false);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Objects.requireNonNull(MainActivity.this)).checkLocationSettings(builder.build());

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
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                MainActivity.this.startIntentSenderForResult(resolvable.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                                // resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException | ClassCastException e) {

                            }
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

    private void getLocation() {
        Intent intent = new Intent(MainActivity.this, TimeChangeReceiver.class);
        boolean alarmUp = (android.app.PendingIntent.getBroadcast(MainActivity.this, 121, intent, android.app.PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp) {


            android.app.AlarmManager alarmMgr = (android.app.AlarmManager) getSystemService(android.content.Context.ALARM_SERVICE);
            android.app.PendingIntent alarmIntent = android.app.PendingIntent.getBroadcast(MainActivity.this, 121, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
            calendar.set(java.util.Calendar.MINUTE, 2);


            alarmMgr.setRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 2 /** 60 * 24*/, alarmIntent);
        } else {
            // Utils.printLog("alarm", "already set");
        }
    }

}
