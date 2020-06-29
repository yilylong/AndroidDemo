package com.zhl.androiddemo.kotlin.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 描述：
 * Created by zhaohl on 2020-6-24.
 */
interface GithubService {
    @GET("{group}/{projectName}")
    fun getGithubDetail(@Path("group")group:String,@Path("projectName")projectName:String):Call<ResponseBody>
}