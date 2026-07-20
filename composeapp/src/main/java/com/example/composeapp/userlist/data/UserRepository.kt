package com.example.composeapp.userlist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Repository 层
 * - 聚合远程、本地数据库、DataStore 三种数据源
 * - 刷新时从远程获取并写入 Room
 * - 平时以 Room 为唯一真相源
 * - 根据 DataStore 偏好过滤 admin 用户
 */
class UserRepository(
    private val remoteDataSource: UserApi,
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) {

    /**
     * 从 Room 读取并过滤，返回响应式 Flow
     */
    val users: Flow<List<User>> = combine(
        userDao.getAllUsers(),
        userPreferences.hideAdminUsers
    ) { entities, hideAdmin ->
        entities
            .map { it.toUser() }
            .filter { user ->
                if (hideAdmin) user.role != "admin" else true
            }
    }

    /**
     * 从远程刷新数据，并替换 Room 缓存
     */
    suspend fun refreshUsers() {
        val remoteUsers = remoteDataSource.getUsers()
        val entities = remoteUsers.map { it.toEntity() }
        userDao.deleteAllUsers()
        userDao.insertUsers(entities)
    }
}
