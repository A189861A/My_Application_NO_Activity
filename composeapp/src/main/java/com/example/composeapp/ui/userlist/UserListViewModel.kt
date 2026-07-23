package com.example.composeapp.ui.userlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.userlist.data.FakeUserRemoteDataSource
import com.example.composeapp.userlist.data.User
import com.example.composeapp.userlist.data.UserDatabase
import com.example.composeapp.userlist.data.UserPreferences
import com.example.composeapp.userlist.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel 层
 * - 管理 UI 状态（Loading / Success / Error）
 * - 处理刷新事件
 * - 暴露 DataStore 偏好供 UI 使用
 */
class UserListViewModel(application: Application) : AndroidViewModel(application) {
    /*
    * getInstance：获取 Room 生成的数据库实例
    * .getInstance(application)：返回 Room 生成的 DAO 实现 (数据库单例)
    * .userDao() ：调用数据库实例里的 userDao() 方法
    * */
    private val userDao = UserDatabase.getInstance(application).userDao()
    private val remoteDataSource = FakeUserRemoteDataSource()
    private val userPreferences = UserPreferences(application)
    private val repository = UserRepository(remoteDataSource, userDao, userPreferences)

    private val _isRefreshing = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    /**
     * UI 状态：Loading / Success / Error
     */
    /*
    * - combine：将多个 Flow 数据流合并成一个新 Flow，当其中任何一个上游 Flow 发射新值时，
    *   它都会将所有 Flow 的最新值凑在一起，计算出一个新结果并发射出去。
    * - .stateIn： 把一个 冷流（Cold Flow） 转换成 热流 StateFlow 给 UI 层监听，生命周期安全，避免重复计算.
    * */
    val uiState: StateFlow<UserListUiState> = combine(
        repository.users,
        _isRefreshing,
        _error
    ) { users, isRefreshing, error ->
        when {
            error != null -> UserListUiState.Error(error)
            isRefreshing -> UserListUiState.Loading
            else -> UserListUiState.Success(users)
        }
    }.stateIn(
        scope = viewModelScope, // 指定协程作用域
        started = SharingStarted.WhileSubscribed(5000), //指定何时启动/停止上游 Flow 的收集
        initialValue = UserListUiState.Loading // 给 StateFlow 一个初始值，订阅时立即能拿到
    )

    /**
     * 是否正在刷新：供 PullToRefreshBox 使用
     */
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * 是否隐藏管理员
     */
    val hideAdmin: StateFlow<Boolean> = userPreferences.hideAdminUsers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        refreshUsers()
    }

    /**
     * 刷新用户列表
     */
    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _error.value = null
            try {
                repository.refreshUsers()
            } catch (e: Exception) {
                _error.value = e.message ?: "刷新失败"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    /**
     * 设置是否隐藏管理员
     */
    fun setHideAdminUsers(hide: Boolean) {
        viewModelScope.launch {
            userPreferences.setHideAdminUsers(hide)
        }
    }
}

/**
 * UI 状态密封类
 */
sealed class UserListUiState {
    /*
    * data object: 普通 object（单例,单例没有构造参数） + data class 的自动特性.
写法          	            实例数量	            自动生成方法	                              适用场景
object A	                 单例	    无自动 toString/equals 优化	                    普通工具单例
data object A	             单例	    toString、equals、hashCode	                    密封层级的无参结果、状态
data class A(val x:Int) 	多实例	    toString、equals、hashCode、copy、componentN	    携带数据实体
   *
   * */

    data object Loading : UserListUiState()

    /*
    *   data class: Kotlin 的数据类(多实例)
      ┌───────────────────────┬─────────────────────────────────────────────────┐
      │    自动生成的方法        │                      作用                       │
      ├───────────────────────┼─────────────────────────────────────────────────┤
      │ toString()            │ "Success(users=[User(id=1,name=...), ...])"     │
      ├───────────────────────┼─────────────────────────────────────────────────┤
      │ equals() / hashCode() │ 基于 users 字段比较两个 Success 是否相等            │
      ├───────────────────────┼─────────────────────────────────────────────────┤
      │ copy()                │ Success(users).copy(users = newList) 复制并修改   │
      ├───────────────────────┼─────────────────────────────────────────────────┤
      │ componentN()          │ val (users) = success 解构声明                   │
      └───────────────────────┴─────────────────────────────────────────────────┘
    * */
    data class Success(val users: List<User>) : UserListUiState()
    data class Error(val message: String) : UserListUiState()
}
