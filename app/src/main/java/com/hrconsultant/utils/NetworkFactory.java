package com.hrconsultant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkFactory
{

    public static NetworkFactory networkFactory;

    public static NetworkFactory getInstance() {
        if (networkFactory == null)
            networkFactory = new NetworkFactory();
        return networkFactory;
    }

    public boolean isNetworkAvailable(Context context)
    {
        if(context!=null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    return true;
                }
            } else {
                // not connected to the internet
                return false;
            }
            return false;
        }
        return false;
    }
}
