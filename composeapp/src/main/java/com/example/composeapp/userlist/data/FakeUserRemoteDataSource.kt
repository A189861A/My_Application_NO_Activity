package com.example.composeapp.userlist.data

import kotlinx.coroutines.delay

/**
 * 模拟远程数据源：离线可用，无需真实 API Key
 * 模拟 1.5 秒网络延迟后返回硬编码列表
 */
class FakeUserRemoteDataSource : UserApi {
    override suspend fun getUsers(): List<UserDto> {
        delay(1500)
        return listOf(
            UserDto(1, "张三", "zhangsan@example.com", "user"),
            UserDto(2, "李四", "lisi@example.com", "admin"),
            UserDto(3, "王五", "wangwu@example.com", "user"),
            UserDto(4, "赵六", "zhaoliu@example.com", "admin"),
            UserDto(5, "孙七", "sunqi@example.com", "user")
        )
    }
}
