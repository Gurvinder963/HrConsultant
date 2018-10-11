package com.hrconsultant.rest;

import android.content.Context;



import java.io.IOException;

import com.hrconsultant.HRApp;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {
    private Context context;

    public CacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder();
        //Response response = chain.proceed(request.build());
        Response response = chain.proceed(request.addHeader("Authorization", HRApp.getInstance().getPreferenceManager().getAccessToken()).build());

        return response;
    }
}
