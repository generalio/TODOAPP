package com.generals.todoapp.model.network

import com.generals.todoapp.model.bean.ChatRequest
import com.generals.todoapp.model.bean.ChatResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/2 23:34
 */

interface ChatService {

    @Headers("Content-Type: application/json")
    @POST("api/aichat/")
    fun chatRequest(@Body request: ChatRequest) : Observable<ChatResponse>

}