
package com.example.app2

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivityUser : AppCompatActivity() {
    private val TAG = "ContentProviderTest"
    // ContentProvider的URI
    /*Uri.parse(String uriString) 是 Uri 类的一个静态方法，用于将字符串形式的 URI 解析为 Uri 对象。*/
    private val USER_URI = Uri.parse("content://com.example.app2.userprovider/user")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_user)

        val btn1= findViewById<Button>(R.id.button1)
        val btn2= findViewById<Button>(R.id.button2)
        val btn3= findViewById<Button>(R.id.button3)
        val btn4= findViewById<Button>(R.id.button4)
        val btn5= findViewById<Button>(R.id.button5)

        // 1. 插入数据
        btn1.setOnClickListener {
            insertUser()
        }

        // 2. 查询所有用户
        btn2.setOnClickListener {
            queryAllUsers()
        }

        // 3. 更新数据（更新id=1的用户）
        btn3.setOnClickListener {
            updateUser(1)
        }
        // 4. 查询单个用户（id=1）
        btn4.setOnClickListener {
            queryUserById(1)
        }

        // 5. 删除数据（删除id=1的用户）
        btn5.setOnClickListener {
            deleteUser(1)
        }
    }

    // 插入用户
    private fun insertUser() {
        val values = ContentValues().apply {
            put("name", "张三")
            put("age", 25)
        }
        /*
        * contentResolver 是 Android 系统中用于 与 ContentProvider 进行交互的核心组件。
        * 它的主要作用是提供一种标准化的方式，让应用程序可以访问和操作其他应用或系统提供的数据。
        * 数据操作：支持对 ContentProvider 提供的数据进行增删改查（CRUD）操作。
            insert()：插入数据。
            query()：查询数据。
            update()：更新数据。
            delete()：删除数据。
        *
        * */
        val newUri = contentResolver.insert(USER_URI, values)
        Log.d(TAG, "插入数据成功，URI: $newUri")
    }

    // 查询所有用户
    private fun queryAllUsers() {
        /*
        * Cursor 是一个接口，用于遍历和操作数据库查询返回的结果集。
        * 它类似于 JDBC 中的 ResultSet，但专为 Android 设计。
        *  主要功能
            数据遍历：通过 moveToFirst()、moveToNext() 等方法逐行读取数据。
            字段访问：通过 getColumnIndex()、getString()、getInt() 等方法获取特定列的值。
            状态管理：提供方法判断是否还有下一行数据（如 isAfterLast()）

         * Cursor? 表示 cursor 变量可以是 Cursor 类型的对象，也可以是 null。
        * */
        val cursor: Cursor? = contentResolver.query(
            USER_URI,
            arrayOf("_id", "name", "age"), // 查询的列
            null, // 无查询条件
            null, // 无条件参数
            "_id DESC" // 按id降序排列
        )

        /*
        * 在 Kotlin 中，let 是一个作用域函数（Scope Function），用于在对象上执行一段代码块
        * cursor?.let：只有当 cursor 不为 null 时，才会执行 let 块内的代码。
        * it：在 let 块中，it 代表 cursor 对象
        *
        * getColumnIndexOrThrow 是 Android 中 Cursor 接口的一个方法
            作用：根据列名查找其在 Cursor 结果集中的索引。
            参数：接受一个字符串参数，表示要查询的列名。
            返回值：返回该列名对应的索引（从 0 开始）。
            异常：如果列名不存在，会抛出 IllegalArgumentException。

        * getInt 是 Android 中 Cursor 接口的一个方法，用于从当前行的指定列中获取整数值。
        * */
        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("_id"))
                val name = it.getString(it.getColumnIndexOrThrow("name"))
                val age = it.getInt(it.getColumnIndexOrThrow("age"))
                Log.d(TAG, "用户信息：id=$id, name=$name, age=$age")
            }
            it.close()
        }
    }

    // 根据id查询用户
    private fun queryUserById(id: Int) {
        /*
        *  Uri.withAppendedPath
        *   作用：将指定的路径段附加到 URI 的末尾，生成一个新的 URI。
            参数：
                第一个参数：原始 Uri 对象。
                第二个参数：要追加的路径段（字符串）。
                返回值：返回一个新的 Uri 对象，包含追加后的完整路径。
        *
        * */
        val uri = Uri.withAppendedPath(USER_URI, id.toString())
        val cursor: Cursor? = contentResolver.query(
            uri,
            arrayOf("_id", "name", "age"),
            null,
            null,
            null
        )
        cursor?.let {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndexOrThrow("name"))
                val age = it.getInt(it.getColumnIndexOrThrow("age"))
                Log.d(TAG, "单个用户信息：id=$id, name=$name, age=$age")
            }
            it.close()
        }
    }

    // 更新用户
    private fun updateUser(id: Int) {
        val uri = Uri.withAppendedPath(USER_URI, id.toString())
        val values = ContentValues().apply {
            put("age", 26) // 将年龄改为26
        }
        val updateCount = contentResolver.update(uri, values, null, null)
        Log.d(TAG, "更新数据成功，影响行数：$updateCount")
    }

    // 删除用户
    private fun deleteUser(id: Int) {
        val uri = Uri.withAppendedPath(USER_URI, id.toString())
        val deleteCount = contentResolver.delete(uri, null, null)
        Log.d(TAG, "删除数据成功，影响行数：$deleteCount")
    }
}