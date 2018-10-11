package com.hrconsultant;

import android.app.Application;

import com.hrconsultant.preference.PreferenceManager;
import com.hrconsultant.rest.RestClient;
import com.hrconsultant.utils.Config;

public class HRApp extends Application{
    private static HRApp instance = null;
    private RestClient restClient;
    private static PreferenceManager preferenceManager;
    public static PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }
    public synchronized static HRApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        restClient = new RestClient(getApplicationContext(), Config.BASE_URL);

        preferenceManager = new PreferenceManager(getApplicationContext());


    }

    public RestClient getRestClient() {
        return restClient;
    }
}
