package com.generals.todoapp.model.repository

import com.generals.todoapp.model.bean.User
import com.generals.todoapp.model.database.AppDataBase

/**
 * @Desc : Login仓库层
 * @Author : zzx
 * @Date : 2025/5/1 15:09
 */

object LoginRepository {

    private val userDao = AppDataBase.getDatabase().userDao()

    fun checkUser(username: String, password: String) = userDao.checkUser(username, password)

    fun checkSign(username: String) = userDao.checkSign(username).map { count -> count > 0 }

    fun insertUser(user: User) = userDao.insertUser(user)

}