package com.example.my_application_no_activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity13 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main13)

        var saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            /*
            * 指定SharedPreferences的文件名为data
            * */
            val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
            editor.putString("name", "Tom")
            editor.putInt("age", 20)
            editor.putBoolean("married", false)
            editor.apply()
        }
    }
}