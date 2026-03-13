package com.example.sunnyweather.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sunnyweather.MainActivity
import com.example.sunnyweather.R

class CityChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_change)

        val edit = findViewById<EditText>(R.id.edit_city)
        val btn = findViewById<Button>(R.id.btn_confirm)

        btn.setOnClickListener {
            val city = edit.text.toString().trim()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("city", city)
            startActivity(intent)
            finish()
        }
    }
}