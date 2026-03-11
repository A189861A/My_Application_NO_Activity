package com.example.app3.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.example.app3.entity.User
import kotlinx.coroutines.flow.Flow

// @Dao：定义 Dao（数据访问对象 → 增删改查），定义数据库操作方法。
@Dao
interface UserDao {
    // 插入单条/多条数据（onConflict = 替换冲突数据）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // 结合协程，避免主线程操作数据库（Room 禁止主线程执行耗时操作）
    suspend fun insertUser(vararg user: User)

    // 更新数据
    @Update
    suspend fun updateUser(user: User)

    // 删除数据
    @Delete
    suspend fun deleteUser(user: User)

    // 删除所有数据（自定义 SQL）
    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    // 查询所有用户（返回 Flow，数据变化时自动通知UI）
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    // 查询所有用户（返回 Flow，数据变化时自动通知UI）
    fun getAllUsers(): Flow<List<User>>

    // 条件查询（根据ID查用户）
    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?
}