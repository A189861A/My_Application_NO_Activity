import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Kotlin版SQLiteOpenHelper，管理数据库创建和版本更新
 */
class MyDatabaseHelper(
    context: Context, // 上下文（建议传Application Context）
    private val dbName: String = DATABASE_NAME, // 数据库名，默认值简化调用
    private val dbVersion: Int = DATABASE_VERSION // 数据库版本号
) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    // 伴生对象：存放静态常量（对应Java的static）
    companion object {
        // 数据库基础配置
        private const val DATABASE_NAME = "MyAppDatabase.db"
        private const val DATABASE_VERSION = 1

        // 用户表相关常量
        const val TABLE_USER = "user"
        const val COLUMN_ID = "_id"       // 主键（SQLite推荐命名）
        const val COLUMN_NAME = "name"    // 姓名
        const val COLUMN_AGE = "age"      // 年龄

        // 创建用户表的SQL语句（Kotlin字符串模板更简洁）
        // Kotlin 支持使用三重引号 ""${'"'} 来定义多行字符串
        private val SQL_CREATE_TABLE_USER = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_AGE INTEGER
            )
        """.trimIndent() // trimIndent去除换行和缩进，保证SQL语法正确

        // 删除用户表的SQL语句
        private val SQL_DROP_TABLE_USER = "DROP TABLE IF EXISTS $TABLE_USER"
    }

    /**
     * 数据库首次创建时调用，初始化表结构
     */
    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL(SQL_CREATE_TABLE_USER)
            Log.d("MyDatabaseHelper", "用户表创建成功")
        } catch (e: Exception) {
            Log.e("MyDatabaseHelper", "创建表失败：${e.message}")
        }
    }

    /**
     * 数据库版本升级时调用（版本号递增触发）
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 版本升级逻辑（示例：按版本号分支处理，避免全量删除）
        when (oldVersion) {
            1 -> {
                // 从版本1升级到2的逻辑（示例：删旧表重建，实际建议数据迁移）
                db.execSQL(SQL_DROP_TABLE_USER)
                db.execSQL(SQL_CREATE_TABLE_USER)
            }
            // 可添加更多版本分支，如2->3的逻辑
            else -> {
                // 通用降级/升级逻辑：删除所有表，重新初始化
                db.execSQL(SQL_DROP_TABLE_USER)
                onCreate(db)
            }
        }
        Log.d("MyDatabaseHelper", "数据库从版本$oldVersion 升级到 $newVersion")
    }

    /**
     * 可选：数据库版本降级时调用（API 11+）
     */
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 降级逻辑复用升级逻辑，也可按需自定义
        onUpgrade(db, oldVersion, newVersion)
    }
}