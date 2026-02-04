package com.example.app2
import MyDatabaseHelper
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.text.TextUtils

/*
* ontentProvider 是 Android 四大组件之一，主要用于在不同应用之间共享数据，也可以用于应用内部的数据管理
*
* */
class UserContentProvider : ContentProvider() {

    // 1. 定义常量（必须唯一，推荐使用包名）
    companion object {
        // Authority：ContentProvider 的唯一标识
        const val AUTHORITY = "com.example.app2.userprovider"
        // 内容URI（用于访问ContentProvider）
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/user")

        // Uri匹配器常量
        private const val USER_TABLE = 1 // 匹配整个用户表
        private const val USER_ID = 2  // 匹配单个用户（通过id）

        // 创建UriMatcher
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "user", USER_TABLE)
            addURI(AUTHORITY, "user/#", USER_ID) // # 匹配任意数字
        }
    }

    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var db: SQLiteDatabase

    // ContentProvider 创建时调用
    /*
    * context: 代表应用程序的上下文环境
    *   context 是 android.content.Context 类的实例，提供了对应用程序环境全局信息的访问。
    *   它允许你访问应用程序的资源（如字符串、图片、布局等）、数据库、文件系统以及系统服务
    *       （如 LayoutInflater、PackageManager 等）。
    *
    * 2. 常见子类
    *   Activity：每个 Activity 都是 Context 的子类，包含与 UI 相关的信息（如主题、窗口等）。
    *   Application：代表整个应用程序的上下文，生命周期与应用程序一致。
    *   Service：后台服务的上下文，不包含 UI 信息
    * */
    override fun onCreate(): Boolean {
        dbHelper = MyDatabaseHelper(context!!)
        db = dbHelper.writableDatabase // 获取可写数据库
        return true
    }

    // 查询数据
    override fun query(
        uri: Uri,
        projection: Array<String>?, // 要查询的列
        selection: String?,         // 查询条件
        selectionArgs: Array<String>?, // 条件参数
        sortOrder: String?          // 排序方式
    ): Cursor? {
        val cursor: Cursor? = when (uriMatcher.match(uri)) {
            USER_TABLE -> {
                // 查询整个用户表
                db.query(
                    "user",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            USER_ID -> {
                // 根据id查询单个用户
                val id = uri.lastPathSegment // 获取URI中的id
                db.query(
                    "user",
                    projection,
                    "_id=?",
                    arrayOf(id),
                    null,
                    null,
                    sortOrder
                )
            }
            else -> throw IllegalArgumentException("未知的URI: $uri")
        }
        // 设置通知Uri，当数据变化时通知ContentResolver
        cursor?.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    // 获取数据类型（MIME类型）
    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            USER_TABLE -> "vnd.android.cursor.dir/vnd.$AUTHORITY.user" // 多条数据
            USER_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.user"   // 单条数据
            else -> null
        }
    }

    // 插入数据
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        require(uriMatcher.match(uri) == USER_TABLE) { "只能插入到用户表，不支持单个用户URI" }
        val rowId = db.insert("user", null, values)
        if (rowId > 0) {
            // 插入成功，构建新的URI（包含新增数据的id）
            val newUri = Uri.withAppendedPath(CONTENT_URI, rowId.toString())
            // 通知数据变化
            context!!.contentResolver.notifyChange(newUri, null)
            return newUri
        }
        return null
    }

    // 删除数据
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleteCount = when (uriMatcher.match(uri)) {
            USER_TABLE -> db.delete("user", selection, selectionArgs)
            USER_ID -> {
                val id = uri.lastPathSegment
                db.delete("user", "_id=?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("未知的URI: $uri")
        }
        if (deleteCount > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return deleteCount
    }

    // 更新数据
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updateCount = when (uriMatcher.match(uri)) {
            USER_TABLE -> db.update("user", values, selection, selectionArgs)
            USER_ID -> {
                val id = uri.lastPathSegment
                db.update("user", values, "_id=?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("未知的URI: $uri")
        }
        if (updateCount > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return updateCount
    }
}