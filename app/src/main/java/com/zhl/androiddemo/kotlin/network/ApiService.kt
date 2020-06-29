package com.zhl.androiddemo.kotlin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 描述：
 * Created by zhaohl on 2020-6-24.
 */
object ApiService {
    private const val BASE_URL = "https://github.com/";
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    fun <T> create(seviceClass:Class<T>):T = retrofit.create(seviceClass)

    inline fun <reified T> create():T = create(T::class.java)
}