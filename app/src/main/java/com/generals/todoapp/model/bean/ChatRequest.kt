package com.generals.todoapp.model.bean

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/2 23:19
 */

data class ChatRequest(
    val messages: List<Message>,
    val model: String,
)

data class Message(
    val content: String,
    val role: String
)