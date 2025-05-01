package com.generals.todoapp.model.repository

import com.generals.todoapp.model.bean.ChildTask
import com.generals.todoapp.model.bean.ParentTask
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.model.database.AppDataBase

/**
 * @Desc : Home的仓库层
 * @Author : zzx
 * @Date : 2025/5/1 20:51
 */

object HomeRepository {

    private val userDao = AppDataBase.getDatabase().userDao()

    fun insertParentTask(parentTask: ParentTask) = userDao.insertParentTask(parentTask)

    fun updateParentTask(parentTask: ParentTask) = userDao.updateParentTask(parentTask)

    fun finishParentTask(taskId: Int) = userDao.finishParentTask(taskId)

    fun changeExpandStatus(taskId: Int) = userDao.changeExpandStatus(taskId)

    fun deleteParentTask(parentTask: ParentTask) = userDao.deleteParentTask(parentTask)

    fun loadAllParentTask(userId: Int) = userDao.loadAllParentTask(userId)

    /*
    fun insertChildTask(childTask: ChildTask) = userDao.insertChildTask(childTask)

    fun updateChildTask(childTask: ChildTask) = userDao.updateChildTask(childTask)

    fun finishChildTask(taskId: Int) = userDao.finishChildTask(taskId)

    fun deleteChildTask(childTask: ChildTask) = userDao.deleteChildTask(childTask)

    fun loadAllChildTask(parentId : Int) = userDao.loadAllChildTask(parentId)

     */

}