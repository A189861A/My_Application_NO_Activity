package com.example.app3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app3.dao.UserDao
import com.example.app3.entity.User
import com.example.app3.model.UserViewModel2

/*
* @Database：定义数据库，指定实体类（表）和数据库版本。
*  - entities 列出所有实体类，version 是数据库版本号
* */
@Database(
    entities = [User::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    /*
    * companion object：伴生对象。(类似Java static)
    * 作用：定义在类内部的单例对象，属于类本身而不是类的实例。
    * 特点：不需要创建类的实例，就可以直接访问其中的成员。
    * */
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            // 双重校验锁，保证单例
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // 用应用上下文，避免内存泄漏
                    AppDatabase::class.java,
                    "app_database" // 数据库文件名
                )
                    // 开发阶段可允许清空数据（上线需移除，改用 Migration）
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}