package com.example.app3.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// 定义表名（默认类名，也可自定义）
@Entity(tableName = "user_table")
data class User(
    // 主键（autoGenerate = true 表示自增）
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    // 字段（可自定义列名，默认字段名）
    // @ColumnInfo(name = "user_name") val name: String,
    val name: String,
    val age: Int,
    val email: String
)