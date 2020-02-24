package com.zhl.androiddemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;

public class CameraXActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTakePic;
    private TextureView textureView;
    Preview preview;
    ImageCapture imageCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        textureView = findViewById(R.id.display_sufaceview);
        btnTakePic = findViewById(R.id.btn_takepicture);
        btnTakePic.setOnClickListener(this);
        startPreview();
    }

    private void startPreview() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},10);
        }else{
            PreviewConfig config = new PreviewConfig.Builder().build();
            preview = new Preview(config);
            preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
                @Override
                public void onUpdated(Preview.PreviewOutput output) {
                    textureView.setSurfaceTexture(output.getSurfaceTexture());
                }
            });
            CameraX.bindToLifecycle(this,preview);
        }
    }

    private void takePicture(){
        ImageCaptureConfig config =
                new ImageCaptureConfig.Builder()
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .build();

        imageCapture = new ImageCapture(config);
        CameraX.bindToLifecycle((LifecycleOwner) this, imageCapture,preview);
        imageCapture.takePicture(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+new Date().getTime()+".jpg"), new ImageCapture.OnImageSavedListener() {
            @Override
            public void onImageSaved(@NonNull File file) {

            }

            @Override
            public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==10&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startPreview();
        }
    }

    @Override
    public void onClick(View view) {
        takePicture();
    }
}
