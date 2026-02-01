package com.example.my_application_no_activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity16 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main16)

        val makeCall = findViewById<Button>(R.id.makeCall)
        makeCall.setOnClickListener {
            try {
                /*
                * 核心作用是封装了一些需要根据 API 级别判断逻辑的操作，
                * 使开发者无需手动处理版本兼容性问题。
                * */
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED // 没有权限
                ) {
                    /*
                    * 主要用于在较低版本的 Android 系统上提供与高版本 API 兼容的功能。
                    * 它的核心作用是简化权限请求和其他兼容性处理。
                    * */
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1
                    )
                } else {
                    call()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
    /*
    * 当应用通过 ActivityCompat.requestPermissions 向用户请求权限后，
    * 用户在系统弹窗中选择“允许”或“拒绝”，系统会自动调用此方法。
    *
    * requestCode: 请求码，用于标识是哪一次权限请求。
    * permissions: 请求的权限数组。
    * grantResults: 权限授予结果数组，每个元素对应 permissions 中的权限是否被授予
    *   （PackageManager.PERMISSION_GRANTED 或 PackageManager.PERMISSION_DENIED）。
    * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when 是 Kotlin 中的条件分支语句，类似于 Java 中的 switch-case
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED //检查第一个权限（即 CALL_PHONE）是否被用户授予。
                ) {
                    call()
                } else {
                    Toast.makeText(
                        this, "You denied the permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //    拨打电话
    private fun call() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}