package com.hrconsultant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import com.hrconsultant.R;
import com.hrconsultant.utils.AppConstants;
import com.hrconsultant.utils.CameraGalleryUtil;
import com.hrconsultant.utils.Utils;


public class FormActivity extends Activity{
    CameraGalleryUtil cameraGalleryUtil;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.form);
        cameraGalleryUtil = new CameraGalleryUtil(this, null);
        iv=findViewById(R.id.ivCapture);
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               selectImage();
           }
       });
    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle("Select Picture From:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Utils.checkForCameraPermissions(FormActivity.this, cameraGalleryUtil);

                } else if (items[item].equals("Choose from Gallery")) {

                    Utils.checkForGalleryPermissions(FormActivity.this, cameraGalleryUtil);


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

            String mPicturePath;
            if (requestCode == AppConstants.CAMERA_REQUEST) {
                if (cameraGalleryUtil.getCameraFilePath() != null) {


                    File file = new File(cameraGalleryUtil.getCameraFilePath());
                    selectedImage = Uri.fromFile(file);
                    mPicturePath = cameraGalleryUtil.getCameraFilePath();

                    Bitmap bitmap = cameraGalleryUtil.getBitmap(mPicturePath, iv.getWidth(), iv.getHeight());
                    iv.setImageBitmap(bitmap);

                }
            }
            else if (requestCode == AppConstants.GALLERY_REQUEST && data != null && data.getData() != null) {


                    selectedImage = data.getData();

                mPicturePath = cameraGalleryUtil.getPath(FormActivity.this, selectedImage);
                Bitmap bitmap = cameraGalleryUtil.getBitmap(mPicturePath, iv.getWidth(), iv.getHeight());
                iv.setImageBitmap(bitmap);


            }
        } else {
            // Image not selected
           // Utils.showToast(getActivity(), "File Not Select");
        }
    }
}
