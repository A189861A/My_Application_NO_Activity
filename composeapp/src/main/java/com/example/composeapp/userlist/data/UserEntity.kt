package com.example.composeapp.userlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room 实体：本地数据库表
 * @Entity 告诉 Room：这个类是数据库表。tableName = "users"
 *
UserEntity 里每个 val 都会变成表里的列：
┌───────────────┬────────────┬─────────┐
│  Kotlin 字段  │ 数据库列名 │  类型   │
├───────────────┼────────────┼─────────┤
│ id: Int       │ id         │ INTEGER │
├───────────────┼────────────┼─────────┤
│ name: String  │ name       │ TEXT    │
├───────────────┼────────────┼─────────┤
│ email: String │ email      │ TEXT    │
├───────────────┼────────────┼─────────┤
│ role: String  │ role       │ TEXT    │
└───────────────┴────────────┴─────────┘
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey // 标记主键
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

fun UserEntity.toUser(): User = User(
    id = id,
    name = name,
    email = email,
    role = role
)
