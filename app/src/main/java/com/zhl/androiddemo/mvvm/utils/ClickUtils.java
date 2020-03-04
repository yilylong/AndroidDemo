package com.zhl.androiddemo.mvvm.utils;

import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * 描述：
 * Created by zhaohl on 2020/3/4.
 */
public class ClickUtils {

    public void changeTextStr(View view){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        Toast.makeText(view.getContext(), "调用类里的方法:"+sb.toString(), Toast.LENGTH_SHORT).show();
    }
}
