package com.hrconsultant.Fragment;

import com.hrconsultant.HRApp;
import com.hrconsultant.LoginActivity;
import com.hrconsultant.MainActivity;

import com.hrconsultant.adapter.FacilitiesDetailAdapter;
import com.hrconsultant.adapter.FaclitiesDetailItem;
import com.hrconsultant.request.CompanyBody;
import com.hrconsultant.request.CustomerBody;
import com.hrconsultant.request.EmployeeBody;
import com.hrconsultant.request.LoginBody;
import com.hrconsultant.request.ReportBody;
import com.hrconsultant.response.CompanyResponse;
import com.hrconsultant.response.CustomerResponse;
import com.hrconsultant.response.EmployeeResponse;
import com.hrconsultant.response.LoginResponse;
import com.hrconsultant.response.ReportResponse;
import com.hrconsultant.utils.AppConstants;
import com.hrconsultant.utils.CameraGalleryUtil;
import com.hrconsultant.utils.CompressImage;
import com.hrconsultant.utils.DateUtils;
import com.hrconsultant.utils.DialogFactory;
import com.hrconsultant.utils.NetworkFactory;
import com.hrconsultant.utils.Utils;
import com.hrconsultant.views.CustomProgressDialog;

import com.hrconsultant.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private CustomProgressDialog customProgressDialog;
    private TimePick tp;
    static int mCompanyId;
    static int mCustomerId;
    static int mEmployeeId;
    private TextView etInTime;
    private TextView etOutTime;
    private EditText edFine;
    private EditText edRemark;
    private Button btnSaveData;
    private TextView tvDate;

    public static HomeFragment newInstance(int comapnyId, int customerId, int employeeid) {
        mCompanyId = comapnyId;
        mCustomerId = customerId;
        mEmployeeId = employeeid;
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private CameraGalleryUtil cameraGalleryUtil;
    private ImageView iv;
    private Button btnAddImage;
    int shirtSelected = -1;
    int pantSelected = -1;
    int tieSelected = -1;
    int capSelected = -1;
    int hairSelected = -1;
    int shaveSelected = -1;
    int shoeSelected = -1;
    int beltSelected = -1;

    String mPicturePath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraGalleryUtil = new CameraGalleryUtil(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report, container, false);

        edFine = v.findViewById(R.id.edFine);
        edRemark = v.findViewById(R.id.edRemark);

        tvDate = v.findViewById(R.id.tvDate);
        tvDate.setText(DateUtils.getInstance().getTodayDate("dd/MMM/yyyy"));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");

            }
        });

        etInTime = v.findViewById(R.id.etInTime);
        etInTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editText = (TextView) view;
                if (tp == null || !tp.isAdded()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    tp = new TimePick(editText);
                    tp.show(ft, "TimePicker");
                }
            }
        });


        etOutTime = v.findViewById(R.id.etOutTime);
        etOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editText = (TextView) view;
                if (tp == null || !tp.isAdded()) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    tp = new TimePick(editText);
                    tp.show(ft, "TimePicker");
                }
            }
        });

        btnAddImage = v.findViewById(R.id.btnAddImage);

        btnSaveData = v.findViewById(R.id.btnSaveData);
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReport();
            }
        });

        iv = v.findViewById(R.id.ivCapture);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        RadioGroup rgShirt = (RadioGroup) v.findViewById(R.id.rgShirt);

        rgShirt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelShirt:
                        shirtSelected = 0;
                        break;
                    case R.id.rbCheckShirt:
                        shirtSelected = 1;
                        break;

                }
            }
        });
        RadioGroup rgShave = (RadioGroup) v.findViewById(R.id.rgShave);

        rgShave.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelShave:
                        shaveSelected = 0;
                        break;
                    case R.id.rbCheckShave:
                        shaveSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgCap = (RadioGroup) v.findViewById(R.id.rgCap);

        rgCap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelCap:
                        capSelected = 0;
                        break;
                    case R.id.rbCheckCap:
                        capSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgTie = (RadioGroup) v.findViewById(R.id.rgTie);

        rgTie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelTie:
                        tieSelected = 0;
                        break;
                    case R.id.rbCheckTie:
                        tieSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgHair = (RadioGroup) v.findViewById(R.id.rgHair);

        rgHair.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelHair:
                        hairSelected = 0;
                        break;
                    case R.id.rbCheckHair:
                        hairSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgBelt = (RadioGroup) v.findViewById(R.id.rgBelt);

        rgBelt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelBelt:
                        beltSelected = 0;
                        break;
                    case R.id.rbCheckBelt:
                        beltSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgShoe = (RadioGroup) v.findViewById(R.id.rgShoe);

        rgShoe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelShoe:
                        shoeSelected = 0;
                        break;
                    case R.id.rbCheckShoe:
                        shoeSelected = 1;
                        break;

                }
            }
        });

        RadioGroup rgPant = (RadioGroup) v.findViewById(R.id.rgPant);

        rgPant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCancelPant:
                        pantSelected = 0;
                        break;
                    case R.id.rbCheckPant:
                        pantSelected = 1;
                        break;

                }
            }
        });


        return v;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Picture From:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Utils.checkForCameraPermissions(getActivity(), cameraGalleryUtil);

                } else if (items[item].equals("Choose from Gallery")) {

                    Utils.checkForGalleryPermissions(getActivity(), cameraGalleryUtil);


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage;


            if (requestCode == AppConstants.CAMERA_REQUEST) {
                if (cameraGalleryUtil.getCameraFilePath() != null) {

                    iv.setVisibility(View.VISIBLE);
                    File file = new File(cameraGalleryUtil.getCameraFilePath());
                    selectedImage = Uri.fromFile(file);
                    mPicturePath = cameraGalleryUtil.getCameraFilePath();

                    Bitmap bitmap = cameraGalleryUtil.getBitmap(mPicturePath, iv.getWidth(), iv.getHeight());
                    iv.setImageBitmap(bitmap);

                }
            } else if (requestCode == AppConstants.GALLERY_REQUEST && data != null && data.getData() != null) {
                iv.setVisibility(View.VISIBLE);

                selectedImage = data.getData();

                mPicturePath = cameraGalleryUtil.getPath(getActivity(), selectedImage);
                Bitmap bitmap = cameraGalleryUtil.getBitmap(mPicturePath, iv.getWidth(), iv.getHeight());
                iv.setImageBitmap(bitmap);


            }
        } else {
            // Image not selected
            // Utils.showToast(getActivity(), "File Not Select");
        }
    }


    private void saveReport() {

        if (NetworkFactory.getInstance().isNetworkAvailable(getActivity())) {
            customProgressDialog = new CustomProgressDialog(getActivity(), R.style.custom_progress_style, true);
            customProgressDialog.show();
            customProgressDialog.setCancelable(false);


            ReportBody body = new ReportBody();
            body.setUserId(Integer.parseInt(HRApp.getPreferenceManager().getLoginId()));
            body.setUnitId(mCustomerId);
            body.setCompanyId(mCompanyId);
            body.setRPT_EmpId(mEmployeeId);
            body.setRPT_Date(tvDate.getText().toString());
            body.setRPT_EmpInTime(etInTime.getText().toString());
            body.setRPT_EmpOutTime(etOutTime.getText().toString());
            body.setRPT_IsShirt(shirtSelected);
            body.setRPT_IsCap(capSelected);
            body.setRPT_IsBelt(beltSelected);
            body.setRPT_IsShoes(shoeSelected);
            body.setRPT_IsPant(pantSelected);
            body.setRPT_IsSahave(shaveSelected);
            body.setRPT_IsHair(hairSelected);
            body.setRPT_IsTie(tieSelected);
            body.setRPT_Fine(Integer.parseInt(edFine.getText().toString()));
            body.setRPT_Remarks(edRemark.getText().toString());
            CompressImage.compress(mPicturePath);
            if (mPicturePath != null) {
                String imgBase = cameraGalleryUtil.getByteString(mPicturePath);
                body.setRPT_Image(imgBase);
                String extension = mPicturePath.substring(mPicturePath.lastIndexOf("."));
                body.setRPT_ImageExt(extension);
            }

            HRApp.getInstance().getRestClient().getUserCredentialService().saveReport(body).enqueue(new Callback<ReportResponse>() {
                @Override
                public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                    if (getActivity() != null && isAdded()) {
                        if (response != null) {
                            if (customProgressDialog.isShowing()) {
                                customProgressDialog.dismiss();
                            }

                            if (response.body().getStatus() == 0) {


                                Toast.makeText(getActivity(), "Data Save successfully.", Toast.LENGTH_LONG).show();
                                getActivity().getFragmentManager().popBackStackImmediate();
                            } else {
                                Toast.makeText(getActivity(), "error in  Saving report.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }


                @Override
                public void onFailure(Call<ReportResponse> call, Throwable t) {

                    if (getActivity() != null && isAdded()) {
                        if (customProgressDialog.isShowing()) {
                            customProgressDialog.dismiss();
                            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
                        }

                    }

                }
            });
        } else {
            DialogFactory.getInstance().showAlertDialog(getActivity(), getString(R.string.app_name), 0, "Network error", "ok", false);
        }
    }

   /* @Override
    public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
        String date = mm + "/" + dd + "/" + yy;
        String formateedDate = DateUtils.getInstance().formatDate("mm/dd/yyyy", "dd/MMM/yyyy", date);
        tvDate.setText(formateedDate);
    }*/

    public static class TimePick extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private TextView editText;

        public TimePick(TextView editText) {
            this.editText = editText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourofDay, int minute) {

            String time=Integer.toString(hourofDay) + ":" + Integer.toString(minute);
            DateUtils.getInstance().formatTime("hh:mm","hh:mm a",time);
            editText.setText(Integer.toString(hourofDay) + ":" + Integer.toString(minute));
        }
    }

    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);



        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String date = month + "/" + day + "/" + year;
            String formateedDate = DateUtils.getInstance().formatDate("mm/dd/yyyy", "dd/MMM/yyyy", date);
            tvDate.setText(formateedDate);
        }

    }
}
