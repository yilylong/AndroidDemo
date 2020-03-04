package com.zhl.androiddemo.mvvm.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.zhl.androiddemo.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * 描述：
 * Created by zhaohl on 2020/3/4.
 */
public class DataBindingHelper {
    // 必须static方法
    @BindingAdapter("loadImgWithGlide")
    public static void loadImageByGlide(ImageView imageView,String url){
        Glide.with(imageView).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transition(withCrossFade())
                .centerCrop()
                .into(imageView);

    }
}
