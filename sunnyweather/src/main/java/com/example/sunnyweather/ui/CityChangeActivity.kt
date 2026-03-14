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
            val intent = Intent()
            intent.putExtra("city", city) // 把数据放入 Intent
            /*
            * 返回数据给上一个Activity
            *
            * 第一个参数：结果码，用于标识返回结果的状态
            *  - 结果码：RESULT_OK 表示成功，RESULT_CANCELED 表示取消
            * */
            setResult(RESULT_OK, intent)
            //关闭当前页面，回到父页面
            finish()
        }
    }
}