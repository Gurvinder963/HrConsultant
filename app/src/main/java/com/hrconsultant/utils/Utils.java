package com.hrconsultant.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.view.inputmethod.InputMethodManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Utils {
    public static void checkForCameraPermissions(Activity activity, final CameraGalleryUtil cameraGalleryUtil) {

        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            cameraGalleryUtil.openCamera();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).check();
    }

    public static void checkForGalleryPermissions(Activity activity, final CameraGalleryUtil cameraGalleryUtil) {
        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            cameraGalleryUtil.openGallery();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).check();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static void replaceFragmentWithoutAnimation(android.app.FragmentManager fragmentManager, Fragment fragment, String tag, boolean addTobackStack, int container) {

        // transaction
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }

        ft.replace(container, fragment, tag);
        ft.commit();
    }
}
