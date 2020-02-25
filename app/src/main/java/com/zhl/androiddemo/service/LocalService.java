package com.zhl.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

import androidx.annotation.Nullable;

/**
 * 描述：
 * Created by zhaohl on 2020-2-25.
 */
public class LocalService extends Service {
    public final IBinder binder = new LocalBinder();
    @Override
    public void onCreate() {
        Log.d("mytag","service_onCreate:");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra("msg_startservice");
        Log.d("mytag","service_onStartCommand:"+msg);
        return Service.START_STICKY;
    }

    public class LocalBinder extends Binder {
        public LocalService getService(){
            return LocalService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("mytag","service_onBind:"+intent.getStringExtra(intent.getStringExtra("msg_bindservice")));
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("mytag","service_onUnBind:");
        stopSelf();
        return super.onUnbind(intent);
    }

    public int getServieData(){
        return new Random().nextInt();
    }

    @Override
    public void onDestroy() {
        Log.d("mytag","service_onDestroy:");
        super.onDestroy();
    }
}
