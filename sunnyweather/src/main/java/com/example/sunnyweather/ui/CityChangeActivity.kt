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

        // spinner 是 Android 中的一个 UI 组件，全称是 Spinner，它是一个下拉列表选择器
        spinner = findViewById(R.id.spinner_city)
        btnConfirm = findViewById(R.id.btn_confirm)

        /*
        * 城市列表来自 assets/cities.json（高德官方行政区划文档），有 338 条，
        * 读取 + 解析放到子线程，避免阻塞主线程；解析完再切回主线程更新 Spinner。
        * */
        loadCities(intent.getStringExtra("currentCity").orEmpty())

        btnConfirm.setOnClickListener {
            // getOrNull: 获取集合中指定索引的元素,如果下标越界，返回 null
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
                /*
                *
                1. assets.open("cities.json")
                    功能：打开 assets 目录下的 cities.json 文件
                    返回：InputStream 对象，用于读取文件内容
                    注意：assets 是应用内置的只读资源目录，适合存放 JSON、文本等配置文件
                *
                2. .bufferedReader()
                    功能：将 InputStream 包装成 BufferedReader
                    优势：提供缓冲功能，提高读取大文件的效率
                    返回：BufferedReader 对象
                *
                3. .use { it.readText() }
                    功能：使用 use 函数来管理资源
                    use 是 Kotlin 的扩展函数，确保资源在使用完毕后自动关闭（即使发生异常）
                    it.readText()：读取整个文件内容为字符串
                    返回：文件内容的字符串
                *
                * */
                val json = assets.open("cities.json").bufferedReader().use { it.readText() }
                /*
                * toList()：将集合或序列转换为不可变列表（List）
                * orEmpty()：如果接收者为 null，返回空列表；否则返回自身
                * */
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
                println("--labels--: $labels")
                /*
                * ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
                    功能：创建一个数组适配器
                    参数：
                    this：上下文（Context）
                    android.R.layout.simple_spinner_item：Spinner 项的布局（默认的单行布局）
                    labels：要显示的数据列表（通常是字符串列表）
                    返回：ArrayAdapter<String> 对象
                 *
                 * .apply { ... }
                    功能：Kotlin 的作用域函数，在适配器对象上执行配置操作
                    优势：避免重复引用对象名称，使代码更简洁
                * */
                spinner.adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, labels)
                        .apply {
                            // 设置下拉列表项的布局为系统提供的下拉布局
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
