package com.generals.todoapp.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Desc : User表
 * @Author : zzx
 * @Date : 2025/4/30 23:47
 */

@Entity(tableName = "users")
data class User(
    var username: String,
    var password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var coin: Int = 10
}
