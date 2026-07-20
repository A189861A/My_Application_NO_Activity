package com.example.composeapp.userlist.data

/**
 * 领域模型：UI 层展示的用户数据
 */
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)
