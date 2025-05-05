package com.generals.todoapp.model.bean

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/2 23:24
 */

data class ChatResponse(
    val answer: String,
    val code: Int,
    val message: String,
    val model: String,
    val msg: String
)