package com.generals.todoapp.model.bean

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/2 23:24
 */

data class ChatResponse(
    val api_source: String,
    val code: Int,
    val content: String,
    val msg: String,
    val reasoning_content: String
)