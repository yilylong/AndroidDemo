package com.zhl.androiddemo.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhl.androiddemo.R;
import com.zhl.androiddemo.databinding.ActivityMvvmDataBindingRecyclerviewBinding;
import com.zhl.androiddemo.databinding.ItemRecyclerviewLayoutBinding;
import com.zhl.androiddemo.mvvm.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * Created by zhaohl on 2020/3/4.
 */
public class DataBindingWithRecyclerView extends AppCompatActivity {
    private ActivityMvvmDataBindingRecyclerviewBinding dataBinding;
    private ArrayList<User> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_data_binding_recyclerview);
        for(int i=0;i<=50;i++){
            data.add(new User("DataBinding recyclerview 测试:张三"+i));
        }
        dataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerview.setAdapter(new MyAdapter(data));
    }


    private class MyAdapter extends RecyclerView.Adapter {
        private List<User> data;

        public MyAdapter(List<User> data){
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             ItemRecyclerviewLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_recyclerview_layout,parent,false);
             return new NewViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            NewViewHolder newViewHolder = (NewViewHolder) holder;
            //如果是多布局，那么对binding进行一个 instansof的一个判断就好。这样我们的ViewHolder永远只需要一个
            ItemRecyclerviewLayoutBinding binding = (ItemRecyclerviewLayoutBinding) newViewHolder.dataBinding;
            binding.setImageUrl("http://bos.pgzs.com/rbpiczy/Wallpaper/2012/1/19/e87e050b8b94415d8a55c121954178b5-13.jpg");
            binding.setItemTextStr(data.get(position).name);
        }

        @Override
        public int getItemCount() {
            return data==null?0:data.size();
        }
    }

    private class NewViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding dataBinding;

        public NewViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }
    }
}
