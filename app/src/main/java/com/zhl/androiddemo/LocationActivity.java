package com.zhl.androiddemo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
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
import com.zhl.androiddemo.recevier.GeofenceBroadcastReceiver;
import com.zhl.androiddemo.service.FetchAddressIntentService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationActivity extends AppCompatActivity implements OnSuccessListener<Location> {
    private Button btnLocationUpdate, btnRegiesterGeofence;
    private TextView locationResultTV;
    private FusedLocationProviderClient locationProviderClient;
    private LocationCallback locationCallback;
    LocationRequest request;
    private AddressResultReceiver resultReceiver;
    private GeofencingClient geofencingClient;
    private Location currentLocation;
    private PendingIntent geofencePendingIntent;
    public static final String GEOFENCE_REQUEST_ID = "MY_GEOFENCE_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        btnLocationUpdate = findViewById(R.id.btn_location_update);
        btnRegiesterGeofence = findViewById(R.id.btn_location_geofencing);
        btnLocationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationUpdate();
            }
        });
        btnRegiesterGeofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regiesterGeofence();
            }
        });
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                for (Location location : locationList) {
                    locationResultTV.setText("维度：" + location.getLatitude() + "-经度：" + location.getLongitude());
                    Log.d("mytag", "维度：" + location.getLatitude() + "-经度：" + location.getLongitude());
                }
                Location location = locationList.get(0);
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(LocationActivity.this, "获取定位成功", Toast.LENGTH_SHORT).show();
                    startAddressService(location);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                currentLocation = null;
                Log.d("mytag", "isLocationAvailability==" + locationAvailability.isLocationAvailable());
                super.onLocationAvailability(locationAvailability);
            }
        };

        resultReceiver = new AddressResultReceiver(new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        });

        locationResultTV = findViewById(R.id.location_result);
        getLastLocation();
    }

    // 注册地理围栏(地理围栏必须要有ACCESS_BACKGROUND_LOCATION权限)
    private void regiesterGeofence() {
        if (currentLocation == null) {
            Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
            return;
        }

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},1001);
        }else {
            if (geofencingClient == null) {
                geofencingClient = LocationServices.getGeofencingClient(this);
            }
            Geofence geofence = new Geofence.Builder()
                    .setCircularRegion(currentLocation.getLatitude(), currentLocation.getLongitude(), 300)
                    .setExpirationDuration(60 * 60 * 1000)
                    .setRequestId(GEOFENCE_REQUEST_ID)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            GeofencingRequest geofencingRequest = getGeofencingRequest(geofence);
            geofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent()).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(LocationActivity.this, "地理围栏创建成功", Toast.LENGTH_SHORT).show();
                    Log.d("mytag", "地理围栏创建成功");
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LocationActivity.this, "地理围栏创建失败", Toast.LENGTH_SHORT).show();
                    Log.d("mytag", "地理围栏创建失败:"+e.getMessage());
                }
            });
        }

    }


    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            if (locationProviderClient == null) {
                locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            }
            locationProviderClient.getLastLocation().addOnSuccessListener(this, this);
        }
    }

    private void requestLocationUpdate() {
        request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(15000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(request);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        // 获取设备设置状态
        Task<LocationSettingsResponse> responseTask = settingsClient.checkLocationSettings(builder.build());
        responseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                LocationSettingsStates settingsStates = locationSettingsResponse.getLocationSettingsStates();
                if (settingsStates != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("BLE_Present: " + settingsStates.isBlePresent() + "\n");// 蓝牙
                    stringBuilder.append("BLE_isUseable: " + settingsStates.isBleUsable() + "\n");// 蓝牙是否可用
                    stringBuilder.append("isGpsPresent: " + settingsStates.isGpsPresent() + "\n");// GPS
                    stringBuilder.append("isGpsUsable: " + settingsStates.isGpsUsable() + "\n");// GPS是否可用
                    stringBuilder.append("isLocationPresent: " + settingsStates.isLocationPresent() + "\n");// 定位
                    stringBuilder.append("isLocationUsable: " + settingsStates.isLocationUsable() + "\n");// 定位是否可用
                    stringBuilder.append("isNetworkLocationPresent: " + settingsStates.isNetworkLocationPresent() + "\n");// 网络定位
                    stringBuilder.append("isNetworkLocationUsable: " + settingsStates.isNetworkLocationUsable() + "\n");// 网络定位是否可用
                    Log.d("mytag", stringBuilder.toString());
                    startLocationRequest();
                }
            }
        });
        responseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        resolvableApiException.startResolutionForResult(LocationActivity.this, 0);
                    } catch (Exception e1) {

                    }
                }
            }
        });
    }

    private void startLocationRequest() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(10000);
        request.setFastestInterval(15000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (locationProviderClient == null) {
            locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        }
        locationProviderClient.requestLocationUpdates(request, locationCallback, null);
    }

    private void stopLocationUpdates() {
        if (locationProviderClient != null) {
            locationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdate();
        } else if(requestCode == 1001 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            regiesterGeofence();
        }else {
            Toast.makeText(this, "获取定位权限失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        requestLocationUpdate();
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
        stopGeofencing();
    }


    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            Log.d("mytag", "获取最近一次的位置信息成功！");
            currentLocation = location;
            // 获取地址
//            starttAddressService(location);
            locationResultTV.setText("最近一次的位置信息:" + location.getLatitude() + "-" + location.getLongitude());
        } else {
            currentLocation = null;
            locationResultTV.setText("获取最近一次位置信息失败");
            Log.d("mytag", "获取最近一次的定位失败！开始请求更新位置信息");
            requestLocationUpdate();
        }
    }

    private void startAddressService(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra("location", location);
        intent.putExtra("resultreciver", resultReceiver);
        startService(intent);
    }

    private void stopGeofencing(){
        if(geofencingClient!=null){
            geofencingClient.removeGeofences(geofencePendingIntent).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("mytag", "停止围栏监控成功");
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("mytag", "停止围栏监控失败");
                }
            });
        }
    }



    class AddressResultReceiver extends ResultReceiver {
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
            if (resultData == null) {
                return;
            }
            String addressResult = resultData.getString("resultMSG");
            if (addressResult == null) {
                locationResultTV.setText("地址为空");
            } else {
                locationResultTV.setText(addressResult);
            }

        }
    }
}
