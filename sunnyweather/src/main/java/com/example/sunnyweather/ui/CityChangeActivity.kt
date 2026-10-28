package com.example.sunnyweather.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sunnyweather.R
import com.example.sunnyweather.model.City
import com.google.gson.Gson

class CityChangeActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var btnConfirm: Button

    // 与 Spinner 的选项一一对应，选中下标即可拿到对应的城市
    private var cities: List<City> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_change)

        spinner = findViewById(R.id.spinner_city)
        btnConfirm = findViewById(R.id.btn_confirm)

        /*
        * 城市列表来自 assets/cities.json（高德官方行政区划文档），有 338 条，
        * 读取 + 解析放到子线程，避免阻塞主线程；解析完再切回主线程更新 Spinner。
        * */
        loadCities(intent.getStringExtra("currentCity").orEmpty())

        btnConfirm.setOnClickListener {
            val city = cities.getOrNull(spinner.selectedItemPosition) ?: return@setOnClickListener
            val intent = Intent()
            intent.putExtra("city", city.adcode) // 把 adcode 放入 Intent
            setResult(RESULT_OK, intent)
            //关闭当前页面，回到父页面
            finish()
        }
    }

    private fun loadCities(currentCity: String) {
        Thread {
            val list = try {
                val json = assets.open("cities.json").bufferedReader().use { it.readText() }
                Gson().fromJson(json, Array<City>::class.java)?.toList().orEmpty()
            } catch (e: Exception) {
                Log.e("CityChangeActivity", "load cities failed", e)
                emptyList()
            }

            runOnUiThread {
                if (isFinishing || isDestroyed) return@runOnUiThread
                cities = list
                if (cities.isEmpty()) {
                    Toast.makeText(this, "城市列表加载失败", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }

                val labels = cities.map { it.displayName }
                spinner.adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, labels).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }

                // 打开页面时默认选中当前正在显示的城市
                val index = cities.indexOfFirst { it.adcode == currentCity }
                if (index >= 0) spinner.setSelection(index)

                btnConfirm.isEnabled = true
            }
        }.start()
    }
}
