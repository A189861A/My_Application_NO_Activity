package com.example.composeapp.userlist.data

/**
 * 网络层 DTO：Retrofit 反序列化后的数据
 */
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    email = email,
    role = role
)
