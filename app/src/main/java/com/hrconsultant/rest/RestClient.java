package com.hrconsultant.rest;

import android.content.Context;
import android.support.annotation.NonNull;


import com.hrconsultant.restservice.UserCredentialService;

import java.io.File;


import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RestClient extends AbstractRestClient {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private UserCredentialService mUserCredentialService;

    public RestClient(Context context, String baseUrl) {
        super(context, baseUrl);
    }

    @Override
    public void initApi() {

        mUserCredentialService = client.create(UserCredentialService.class);
    }

    public UserCredentialService getUserCredentialService() {

        return mUserCredentialService;
    }
    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }
}
