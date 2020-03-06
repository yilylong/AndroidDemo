package com.zhl.androiddemo.mvvm;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.zhl.androiddemo.R;
import com.zhl.androiddemo.databinding.ActivityLiveDataBinding;
import com.zhl.androiddemo.mvvm.bean.Dog;
import com.zhl.androiddemo.mvvm.viewmodel.LiveDataViewModel;

public class LiveDataActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLiveDataBinding binding;
    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private LiveDataViewModel viewModel;
    private Dog dog = new Dog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_live_data);

        binding.setOnlyLive(liveData.getValue());
        binding.setClickListener(this);
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.setOnlyLive(s);
            }
        });
        binding.setDog(dog);

        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        binding.setLivedataViewModel(viewModel);
    }
    @Override
    protected void onStop() {
        super.onStop();
        liveData.postValue("单独LiveData使用");
        dog.setType("贵兵犬");
        viewModel.getLiveData().postValue("LiveData和ViewModel结合使用");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_only_live:
                liveData.setValue("使用livedata setValue()同线程更新");
                break;
        }
    }
}
