package com.zhl.androiddemo.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zhl.androiddemo.R;
import com.zhl.androiddemo.databinding.ActivityMvvmBinding;

public class MvvmActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMvvmBinding activityMvvmBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvvm);
        activityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        activityMvvmBinding.setBtnDatabindingStr("DataBindingTest");
        activityMvvmBinding.setBtnDataBindingListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_databinding_test:
                Intent intent = new Intent(this, MvvmDataBindingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
