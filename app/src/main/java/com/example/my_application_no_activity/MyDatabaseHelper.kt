package com.example.my_application_no_activity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//SQLiteOpenHelper 是 Android 平台提供的一个用于管理 SQLite 数据库创建和版本管理的抽象基类。
class MyDatabaseHelper(val context: Context, val name: String, val version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    /*
    * 创建Book表
    * */
    private val createBook = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "category_id integer, " +
            "name text)"

    private val createCategory = "create table Category (" +
            "id integer primary key autoincrement, " +
            "category_name text, " +
            "category_code integer)"

//    onCreate 方法在数据库首次创建时被系统自动调用，此时 db 已经初始化完毕，可以直接用于执行数据库操作
//    db 是 SQLiteDatabase 类的一个实例，表示一个 SQLite 数据库连接
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBook)
        db.execSQL(createCategory)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }
//    数据库版本更新时调用
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("drop table if exists Book")
//        db.execSQL("drop table if exists Category")
//        onCreate(db)

        if (oldVersion <= 1) {
            // 检查 Category 表是否存在
//            rawQuery 是 Android 中 SQLiteDatabase 类的一个方法，用于执行原始的 SQL 查询语句并返回一个 Cursor 对象
            /*
            * sqlite_master 表记录了当前数据库中所有对象（如表、索引等）的定义信息。
            * 每一行代表一个数据库对象，包含对象的名称、类型、创建语句等。
            *
            * */
            val cursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='Category'", null
            )
            if (!cursor.moveToFirst()) {
                db.execSQL(createCategory)
            }
            cursor.close()
        }
        if (oldVersion <= 2) {
            db.execSQL("alter table Book add column category_id integer")
        }
    }
}


















