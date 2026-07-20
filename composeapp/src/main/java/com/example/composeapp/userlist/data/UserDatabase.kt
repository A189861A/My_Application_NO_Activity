package com.example.composeapp.userlist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room 数据库：单例模式
 *
 * 加 @Database 注解后，Room 编译器会生成这个抽象类的具体实现子类（比如
 *   UserDatabase_Impl）。
 *   - 生成的实现类里会实现 userDao()，返回一个 UserDao 的具体实现。
 */
@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao // 这是一个抽象方法，声明了这个数据库有哪些 DAO。

    companion object {
        /*
        * @Volatile 保证所有线程读取到的都是最新写入的值。
        * */
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( // 创建SQLite数据库
                    context.applicationContext,
                    UserDatabase::class.java,
                    /*
                    * Room 数据库文件名：user_database
                    * 存储位置 : /data/data/你的包名/databases/user_database
                    * */
                    "user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
