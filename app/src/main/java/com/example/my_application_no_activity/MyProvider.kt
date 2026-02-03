package com.example.my_application_no_activity

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

/*
* 标准的内容URI写法是：
*   content://com.example.app.provider/table1 (访问的是com.example.app这个应用的table1表中的数据)
*   content://com.example.app.provider/table1/1 (访问的是com.example.app这个应用的table1表中id为1的数据)
*
*   *表示匹配任意长度的任意字符,
*   #表示匹配任意长度的数字
*
* */
class MyProvider : ContentProvider() {
    private val table1Dir = 0
    private val table1Item = 1
    private val table2Dir = 2
    private val table2Item = 3

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH);
    init {
        uriMatcher.addURI("com.example.app.provider", "table1", table1Dir)
        uriMatcher.addURI("com.example.app.provider ", "table1/#", table1Item)
        uriMatcher.addURI("com.example.app.provider ", "table2", table2Dir)
        uriMatcher.addURI("com.example.app.provider ", "table2/#", table2Item)
    }

    override fun onCreate(): Boolean {
        return false
    }
    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        when (uriMatcher.match(uri)) {
            table1Dir -> {
                // 查询table1表中的所有数据
            }

            table1Item -> {
                // 查询table1表中的单条数据
            }

            table2Dir -> {
                // 查询table2表中的所有数据
            }

            table2Item -> {
                // 查询table2表中的单条数据
            }
        }
        return TODO("Provide the return value")
    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }
    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
    /*
    * 用于获取Uri对象所对应的MIME类型.
    *
    * 对于content://com.example.app.provider/table1这个内容URI，它所对应的MIME类型就可以写成：
    * vnd.android.cursor.dir/vnd.com.example.app.provider.table1
    * */
    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        table1Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table1"
        table1Item -> "vnd.android.cursor.item/vnd.com.example.app.provider.table1"
        table2Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table2"
        table2Item -> "vnd.android.cursor.item/vnd.com.example.app.provider.table2"
        else -> null
    }
}