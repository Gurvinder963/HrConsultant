package com.hrconsultant.preference;

import android.content.Context;

import com.hrconsultant.BuildConfig;
import com.hrconsultant.utils.AESHelper;


public class PreferenceManager extends AbstractPreferenceManager {


    private static final int VERSION = 1;
    private static final String PREF_NAME = "HR";
    private static final String PREF_KEY_ACCESS_TOKEN = "access_token";
    private static final String PREF_KEY_LOGIN_ID = "login_id";







    private Context mContext;

    public PreferenceManager(Context ctx) {
        super(ctx, PREF_NAME, VERSION);
        mContext = ctx;
    }
   public String getAccessToken() {

            return readString(PREF_KEY_ACCESS_TOKEN, "");

    }


    public String getLoginId() {
        return readString(PREF_KEY_LOGIN_ID, "");
    }

    public void setLoginId(String loginId) {
        saveString(PREF_KEY_LOGIN_ID, loginId);
    }


    @Override
    public boolean clearPreferences() {
        return super.clearPreferences();
    }

}