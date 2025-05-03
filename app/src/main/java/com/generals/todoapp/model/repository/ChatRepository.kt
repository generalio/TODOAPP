package com.generals.todoapp.model.repository

import com.generals.todoapp.model.bean.ChatRequest
import com.generals.todoapp.model.network.ChatService
import com.generals.todoapp.model.network.ServiceCreator

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/3 00:00
 */

object ChatRepository {

    private val retrofit = ServiceCreator.create<ChatService>()

    fun chat(chatRequest: ChatRequest) = retrofit.chatRequest(chatRequest)

}