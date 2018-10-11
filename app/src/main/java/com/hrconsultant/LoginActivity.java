package com.hrconsultant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hrconsultant.request.LoginBody;
import com.hrconsultant.response.LoginResponse;
import com.hrconsultant.utils.DialogFactory;
import com.hrconsultant.utils.NetworkFactory;
import com.hrconsultant.utils.Utils;
import com.hrconsultant.views.CustomProgressDialog;

import com.hrconsultant.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity{

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private NestedScrollView rootView;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        rootView=findViewById(R.id.rootView);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUserName.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter username and password",Toast.LENGTH_SHORT).show();

                }
                else {

                    customProgressDialog = new CustomProgressDialog(LoginActivity.this, R.style.custom_progress_style, true);
                    customProgressDialog.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getLoginApiCall();
                        }
                    }, 200);


                }
            }
        });

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.hideSoftKeyboard(LoginActivity.this);
            }
        });
    }

    private void getLoginApiCall() {

        if (NetworkFactory.getInstance().isNetworkAvailable(this)) {

            LoginBody body = new LoginBody();
            body.setUsername(etUserName.getText().toString());
            body.setPassword(etPassword.getText().toString());

            HRApp.getInstance().getRestClient().getUserCredentialService().getLoginData(body).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if(response!=null) {
                            if (customProgressDialog.isShowing()) {
                                customProgressDialog.dismiss();
                            }


                               if(response.body().getStatus()==0) {
                                   HRApp.getPreferenceManager().setLoginId(String.valueOf(response.body().getData().getId()));
                                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                   startActivity(intent);
                                   finish();
                               }
                               else if(response.body().getStatus()==1){
                                   AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                   builder.setMessage("Email or password is not correct!")
                                           .setCancelable(false)
                                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog, int id) {
                                                   //do things
                                               }
                                           });
                                   AlertDialog alert = builder.create();
                                   alert.show();
                               }
                                // SearchFragment fragment = SearchFragment.newInstance();
                                //Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment, SearchFragment.class.getSimpleName(), true, R.id.fragmentContainer);


                        }
                    }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {


                        if (customProgressDialog.isShowing()) {
                            customProgressDialog.dismiss();
                            DialogFactory.getInstance().showAlertDialog(LoginActivity.this, getString(R.string.app_name), 0, "Network error", "ok", false);
                        }



                }
            });
        } else {
            DialogFactory.getInstance().showAlertDialog(LoginActivity.this, getString(R.string.app_name), 0, "Network error", "ok", false);
        }
    }
}
