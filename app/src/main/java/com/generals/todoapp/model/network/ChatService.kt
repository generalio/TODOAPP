package com.generals.todoapp.model.network

import com.generals.todoapp.model.bean.ChatResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/2 23:34
 */

interface ChatService {

    @Headers("Content-Type: application/json")
    @GET("api/xfai/")
    fun chatRequest(@Query("message") message: String) : Observable<ChatResponse>

}