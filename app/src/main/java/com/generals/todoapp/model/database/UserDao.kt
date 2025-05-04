package com.generals.todoapp.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.generals.todoapp.model.bean.ChildTask
import com.generals.todoapp.model.bean.ParentTask
import com.generals.todoapp.model.bean.User
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
 * @Desc : Dao
 * @Author : zzx
 * @Date : 2025/5/1 10:28
 */

@Dao
interface UserDao {

    // 用户的增加
    @Insert
    fun insertUser(user: User) : Single<Long>

    //判断账号密码是否正确
    @Query("select * from users where username = :username and password = :password")
    fun checkUser(username: String, password: String) : Single<User>

    //判断账户是否存在
    @Query("select count(*) from users where username = :username")
    fun checkSign(username: String) : Single<Int>

    @Query("select * from users where id = :userId")
    fun getUser(userId: Int) : Single<User>

    //添加父任务
    @Insert
    fun insertParentTask(parentTask: ParentTask) : Single<Long>

    //更新父任务
    @Update
    fun updateParentTask(parentTask: ParentTask) : Single<Int>

    //更新父任务的置顶状态
    @Query("update parent_task set top = case when top = 0 then 1 else 0 end where id = :taskId")
    fun updateTopStatus(taskId: Int) : Single<Int>

    //更新父任务的完成状态
    @Query("update parent_task set finish = case when finish = 0 then 1 else 0 end where id = :taskId")
    fun finishParentTask(taskId: Int) : Single<Int>

    //删除父任务
    @Delete
    fun deleteParentTask(parentTask: ParentTask) : Single<Int>

    //查找所有父任务
    @Query("select * from parent_task  where user_id = :userId order by  top, finish, id")
    fun loadAllParentTask(userId: Int) : Flowable<List<ParentTask>>

    //添加子任务
    @Insert
    fun insertChildTask(childTask: ChildTask) : Single<Long>

    //更新子任务
    @Update
    fun updateChildTask(childTask: ChildTask) : Single<Int>

    //更新子任务的完成状态
    @Query("update child_task set finish = case when finish = 0 then 1 else 0 end where id = :taskId")
    fun finishChildTask(taskId: Int) : Single<Int>

    //删除子任务
    @Delete
    fun deleteChildTask(childTask: ChildTask) : Single<Int>

    //查找用户下的所有子任务
    @Query("select * from child_task where user_id = :userId")
    fun loadAllChildTask(userId: Int) : Flowable<List<ChildTask>>

    //更新积分数量
    @Query("update users set coin = :coin where id = :userId")
    fun updateCoin(userId: Int, coin: Int) :Single<Int>

}