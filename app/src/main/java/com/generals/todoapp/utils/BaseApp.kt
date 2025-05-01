package com.generals.todoapp.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @Desc : 全局Application
 * @Author : zzx
 * @Date : 2025/5/1 10:45
 */

class BaseApp() : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        //获取全局context
        context = applicationContext
    }

}