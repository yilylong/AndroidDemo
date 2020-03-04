package com.zhl.androiddemo.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zhl.androiddemo.R;
import com.zhl.androiddemo.databinding.ActivityMvvmDataBindingBinding;
import com.zhl.androiddemo.mvvm.bean.User;
import com.zhl.androiddemo.mvvm.utils.ClickUtils;

public class MvvmDataBindingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMvvmDataBindingBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvvm_data_binding);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_data_binding);
        viewDataBinding.setTextStr("设置数据");
        viewDataBinding.setClickListener(this);
        viewDataBinding.setClickUtils(new ClickUtils());
        User user  =  new User();
        user.name = "张三";
        user.setHigh(170);
        user.setWeight(60);
        viewDataBinding.setUserinfo(user);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dataBinding_test:
                viewDataBinding.tvDataBindingTest.setText("点击更改数据");
                break;
            case R.id.tv_recylerview:
                Intent intent = new Intent(this,DataBindingWithRecyclerView.class);
                startActivity(intent);
                break;
        }
    }
}
