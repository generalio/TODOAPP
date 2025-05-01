package com.generals.todoapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.generals.todoapp.model.bean.ChildTask
import com.generals.todoapp.model.bean.ParentTask
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.utils.BaseApp

/**
 * @Desc : 数据库创建
 * @Author : zzx
 * @Date : 2025/5/1 10:41
 */

@Database(version = 1, entities = [User::class, ParentTask::class, ChildTask::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        private var instance: AppDataBase? = null
        @Synchronized
        fun getDatabase(): AppDataBase {
            val context = BaseApp.context
            instance?.let {
                return it
            }
            return  Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "app_database")
                .build().apply {
                    instance = this
                }
        }
    }

}