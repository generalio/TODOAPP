package com.generals.todoapp.model.repository

import com.generals.todoapp.model.database.AppDataBase

/**
 * @Desc : Main的仓库层
 * @Author : zzx
 * @Date : 2025/5/1 16:41
 */

object MainRepository {

    private val userDao = AppDataBase.getDatabase().userDao()

    fun getUser(userId: Int) = userDao.getUser(userId)

}