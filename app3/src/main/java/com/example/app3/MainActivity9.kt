package com.example.app3

import android.Manifest // Android 系统的权限清单类，包含了所有 Android 系统预定义的权限常量。
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mylibrary.PermissionX

class MainActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main9)

        val makeCallBtn = findViewById<Button>(R.id.makeCallBtn)
        makeCallBtn.setOnClickListener {
            PermissionX.request(this,
                Manifest.permission.CALL_PHONE) { allGranted, deniedList ->
                if (allGranted) {
                    call()
                } else {
                    Toast.makeText(this, "You denied $deniedList",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun call() {
        try {
            val intent = android.content.Intent(android.content.Intent.ACTION_CALL)
            intent.data = android.net.Uri.parse("tel:10086")
            startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}