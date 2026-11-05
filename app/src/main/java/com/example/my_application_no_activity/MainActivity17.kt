package com.example.my_application_no_activity

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity17 : AppCompatActivity() {
    private val TAG = "ClientApp"

    // 核心：必须和提供方的Authority完全一致
    private val PROVIDER_AUTHORITY = "com.example.app2.userprovider"

    /*
    * 构造一个指向特定数据提供者的统一资源标识符（URI）。
    content://    com.example.app.provider    /user
       ^                    ^                   ^
       |                    |                   |
    协议头           Authority (授权信息)        Path (路径)

    * */
    private val USER_URI = Uri.parse("content://$PROVIDER_AUTHORITY/user")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main17)

        val queryData = findViewById<Button>(R.id.queryData)
        queryData.setOnClickListener {
            Toast.makeText(this, "查询数据", Toast.LENGTH_SHORT).show()
            queryUserFromProvider()
        }
    }

    // 读取提供方的数据
    private fun queryUserFromProvider() {
        /*
        * contentResolver.query ：跨应用查询数据的核心方法.
        * */
        val cursor: Cursor? = contentResolver.query(
            USER_URI, // 1. Uri: 数据地址
            arrayOf("_id", "name", "age"), // 2. 查询哪些列
            null, // / 3. 查询条件
            null,// 4. 查询条件的参数
            "_id DESC"// 5. 排序规则
        )
        cursor?.let {
            // 循环遍历每一行数据
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow("_id"))
                val name = it.getString(it.getColumnIndexOrThrow("name"))
                val age = it.getInt(it.getColumnIndexOrThrow("age"))
                Log.d(TAG, "从ProviderApp读取到数据：id=$id, name=$name, age=$age")
            }
            it.close()
        } ?: Log.e(TAG, "读取数据失败，cursor为空")
    }

}