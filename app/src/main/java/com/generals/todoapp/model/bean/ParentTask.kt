package com.generals.todoapp.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Desc : 父类列表
 * @Author : zzx
 * @Date : 2025/4/30 23:53
 */

@Entity(tableName = "parent_task")
data class ParentTask(
    var title: String,
    var desc: String,
    @ColumnInfo(name = "user_id")
    var userId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    //top为0 -> 置顶 1 -> 不置顶
    var top: Int = 1
    //finish为0 -> 未完成 1 -> 已完成
    var finish: Int = 0
}
