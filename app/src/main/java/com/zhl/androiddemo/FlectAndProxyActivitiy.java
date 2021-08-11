package com.zhl.androiddemo;

import android.os.Bundle;
import android.view.View;

import com.zhl.androiddemo.bean.Humam;
import com.zhl.androiddemo.rxjava.LogDebug;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 描述：
 * Created by zhaohl on 2021/3/8.
 */

public class FlectAndProxyActivitiy extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flect_proxy);
        findViewById(R.id.btn_reflect).setOnClickListener(this);
        findViewById(R.id.btn_proxy).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reflect:
                testReflect();
                break;
            case R.id.btn_proxy:

                break;
        }
    }

    private void testReflect() {
        try {
            Class<Humam> humanClass = (Class<Humam>) Class.forName("com.zhl.androiddemo.bean.Humam");
            LogDebug.d("类加载器：" + humanClass.getClassLoader());
            Constructor<Humam>[] constructors = (Constructor<Humam>[]) humanClass.getConstructors();
            for(Constructor constructor:constructors){
                LogDebug.d(constructor.getName()+"-"+constructor.toString());
                Class<?>[] Paramclazzs = constructor.getParameterTypes();
                for(Class c:Paramclazzs){
                    LogDebug.d("构造器参数："+c.getName());
                }
            }
            Constructor<Humam> humamConstructor = humanClass.getConstructor(String.class,int.class,int.class);
            Humam humam = humamConstructor.newInstance("张三",188,80);
            humam.setGender("女");
            LogDebug.d("通过constructor的newInstance构造对象："+humam.name);

            Method[] methods = humanClass.getMethods();
            for(Method method:methods){
                LogDebug.d("获取方法："+method.getName());
            }
            LogDebug.d("反射方法setGender");
            Method method = humanClass.getMethod("setGender",String.class);
            method.setAccessible(true);
            method.invoke(humam,"男");

            Method method2 = humanClass.getMethod("getGender");
            String gender = (String) method2.invoke(humam);
            LogDebug.d("反射方法getGender()="+gender);

            Field[] fields = humanClass.getDeclaredFields();
            for(Field field:fields){
                LogDebug.d("反射字段："+field.getName());
            }
            Field field = humanClass.getDeclaredField("gender");
            field.setAccessible(true);
            field.set(humam,"人妖");
            LogDebug.d((String) field.get(humam));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
