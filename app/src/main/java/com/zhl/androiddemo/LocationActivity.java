package com.zhl.androiddemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.zhl.androiddemo.service.FetchAddressIntentService;

import java.util.List;

public class LocationActivity extends AppCompatActivity implements OnSuccessListener<Location> {
    private Button btnLocationUpdate;
    private TextView locationResultTV;
    private FusedLocationProviderClient locationProviderClient;
    private LocationCallback locationCallback;
    LocationRequest request;
    private AddressResultReceiver resultReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btnLocationUpdate = findViewById(R.id.btn_location_update);
        btnLocationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationUpdate();
            }
        });
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                for(Location location:locationList){
//                    locationResultTV.setText("维度："+location.getLatitude()+"-经度："+location.getLongitude());
                    Log.d("mytag","维度："+location.getLatitude()+"-经度："+location.getLongitude());
                }
                Location location = locationList.get(0);
                if(location!=null){
                    Toast.makeText(LocationActivity.this,"获取定位成功",Toast.LENGTH_SHORT).show();
                    starttAddressService(location);
                }
            }
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                Log.d("mytag","isLocationAvailability=="+locationAvailability.isLocationAvailable());
                super.onLocationAvailability(locationAvailability);
            }
        };

        resultReceiver = new AddressResultReceiver(new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        });

        locationResultTV = findViewById(R.id.location_result);
        getLastLocation();
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }else{
            if(locationProviderClient==null){
                locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            }
            locationProviderClient.getLastLocation().addOnSuccessListener(this,this);
        }
    }

    private void requestLocationUpdate() {
        request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(request);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        // 获取设备设置状态
        Task<LocationSettingsResponse> responseTask = settingsClient.checkLocationSettings(builder.build());
        responseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                LocationSettingsStates settingsStates = locationSettingsResponse.getLocationSettingsStates();
                if(settingsStates!=null){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("BLE_Present: "+settingsStates.isBlePresent()+"\n");// 蓝牙
                    stringBuilder.append("BLE_isUseable: "+settingsStates.isBleUsable()+"\n");// 蓝牙是否可用
                    stringBuilder.append("isGpsPresent: "+settingsStates.isGpsPresent()+"\n");// GPS
                    stringBuilder.append("isGpsUsable: "+settingsStates.isGpsUsable()+"\n");// GPS是否可用
                    stringBuilder.append("isLocationPresent: "+settingsStates.isLocationPresent()+"\n");// 定位
                    stringBuilder.append("isLocationUsable: "+settingsStates.isLocationUsable()+"\n");// 定位是否可用
                    stringBuilder.append("isNetworkLocationPresent: "+settingsStates.isNetworkLocationPresent()+"\n");// 网络定位
                    stringBuilder.append("isNetworkLocationUsable: "+settingsStates.isNetworkLocationUsable()+"\n");// 网络定位是否可用
                    Log.d("mytag",stringBuilder.toString());
                    startLocationRequest();
                }
            }
        });
        responseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    try{
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        resolvableApiException.startResolutionForResult(LocationActivity.this,0);
                    }catch (Exception e1){

                    }
                }
            }
        });
    }

    private void startLocationRequest() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationProviderClient.requestLocationUpdates(request,locationCallback,null);
    }

    private void stopLocationUpdates() {
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            requestLocationUpdate();
        }else{
            Toast.makeText(this,"获取定位权限失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSuccess(Location location) {
        if(location!=null){
            Log.d("mytag","获取最近的定位成功！");
            // 获取地址
            starttAddressService(location);
//            locationResultTV.setText(location.getLatitude()+"-"+location.getLongitude());
        }else{
            Log.d("mytag","获取最近的定位失败！");
//            requestLocationUpdate();
        }
    }

    private void starttAddressService(Location location) {
        Intent intent  = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra("location",location);
        intent.putExtra("resultreciver",resultReceiver);
        startService(intent);
    }

    class AddressResultReceiver extends ResultReceiver{
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if(resultData==null){
                return;
            }
            String addressResult = resultData.getString("resultMSG");
            if(addressResult==null){
                locationResultTV.setText("地址为空");
            }else{
                locationResultTV.setText(addressResult);
            }

        }
    }
}
