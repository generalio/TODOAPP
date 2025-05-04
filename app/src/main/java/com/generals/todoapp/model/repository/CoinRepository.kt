package com.generals.todoapp.model.repository

import com.generals.todoapp.model.database.AppDataBase

/**
 * @Desc : Coin仓库层
 * @Author : zzx
 * @Date : 2025/5/4 13:28
 */

object CoinRepository {

    private val userDao = AppDataBase.getDatabase().userDao()

    fun updateCoin(userId: Int, coin: Int) = userDao.updateCoin(userId, coin)

    fun getUser(userId: Int) = userDao.getUser(userId)

}