package com.zhl.androiddemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;

public class FetchAddressIntentService extends IntentService {
    protected ResultReceiver resultReceiver;
    public FetchAddressIntentService() {
        super("");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchAddressIntentService(String name) {
        super(name);
//        resultReceiver = new ResultReceiver.MyResultReceiver();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        resultReceiver = intent.getParcelableExtra("resultreciver");
        if(intent==null){
            return;
        }
        String errorMessage="";
        Geocoder geocoder = new Geocoder(this, Locale.CHINA);
        Location location = intent.getParcelableExtra("location");
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "服务不可用";
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "无效的经纬度";
        }
        if(addressList==null||addressList.isEmpty()){
            if(errorMessage.isEmpty()){
                errorMessage = "没有找到对应的地址";
            }
            deliverResultToReceiver(1,errorMessage);
        }else{
            Address address = addressList.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            deliverResultToReceiver(2,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode,String msg){
        Bundle bundle = new Bundle();
        bundle.putString("resultMSG",msg);
        resultReceiver.send(resultCode,bundle);
    }
}
