package com.generals.todoapp.model.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Desc : Retrofit构建器
 * @Author : zzx
 * @Date : 2025/5/2 23:14
 */

object ServiceCreator {

    private const val BASE_URL = "https://api.pearktrue.cn/"

    //API太烂了，加点时间
    val client = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)     // 读取超时时间
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)

}