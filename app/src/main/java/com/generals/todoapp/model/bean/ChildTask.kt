package com.generals.todoapp.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Desc : 子任务
 * @Author : zzx
 * @Date : 2025/5/1 00:03
 */

@Entity(tableName = "child_task")
data class ChildTask(
    var title: String,
    @ColumnInfo(name = "parent_id")
    var parentId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    //finish为0 -> 未完成 1 -> 已完成
    var finish: Int = 0
}
