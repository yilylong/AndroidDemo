package com.zhl.androiddemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhl.androiddemo.service.LocalService;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceTestActivity extends AppCompatActivity {
    private TextView tvResult;
    private Button btnStartService, btnBindService, btnGetData;
    LocalServiceConnection connection;
    LocalService localService;
    private boolean mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        tvResult = findViewById(R.id.tv_msg);
        btnStartService = findViewById(R.id.btn_startservice);
        btnBindService = findViewById(R.id.btn_bindservice);
        btnGetData = findViewById(R.id.btn_getservicedata);
        connection = new LocalServiceConnection();
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });
        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService();
            }
        });
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getServiceData();
            }
        });

    }

    public void startService() {
        Intent intent = new Intent(this, LocalService.class);
        intent.putExtra("msg_startservice","启动服务");
        startService(intent);
    }

    public void bindService() {
        Intent intent = new Intent(this, LocalService.class);
        intent.putExtra("msg_bindservice","绑定服务");
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }


    private void getServiceData() {
        if(localService==null){
            bindService();
            return;
        }
        tvResult.setText("服务端数据："+localService.getServieData());
    }

    private class LocalServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) iBinder;
            localService = binder.getService();
            tvResult.setText("服务端数据："+localService.getServieData());
            mBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBind = false;
        }
    }

    @Override
    protected void onDestroy() {
        if(mBind){
            unbindService(connection);
        }
        super.onDestroy();
    }
}
