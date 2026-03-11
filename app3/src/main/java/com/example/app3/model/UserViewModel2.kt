package com.example.app3.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app3.database.AppDatabase
import com.example.app3.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/*
* 在 ViewModel 中封装数据库操作（符合 MVVM 规范），避免直接在 Activity/Fragment 中操作
* ViewModel：定义视图模型，用于存储和管理 UI 相关数据。
* */
class UserViewModel2(application: Application) : AndroidViewModel(application) {
    // 获取数据库实例和 Dao
    private val db = AppDatabase.getInstance(application)
    private val userDao = db.userDao()

    // 观察所有用户数据（供UI层观察）
    /*
    * Flow：响应式编程，数据流，当数据发生变化时，自动通知观察者
    * Flow<List<User>>：数据流类型，数据为用户列表
    */
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    // 插入用户（ViewModel 协程作用域，自动跟随生命周期）
    fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insertUser(user)
        }
    }

    // 更新用户
    fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.updateUser(user)
        }
    }

    // 删除用户
    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUser(user)
        }
    }

    // 根据ID查用户
    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
}