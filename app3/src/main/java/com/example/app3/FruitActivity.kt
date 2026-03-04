package com.example.app3

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class FruitActivity : AppCompatActivity() {
    companion object {
        const val FRUIT_NAME = "fruit_name"
        const val FRUIT_IMAGE_ID = "fruit_image_id"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fruit)
        // 处理窗口Insets，避免Toolbar被状态栏遮挡
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // 获取系统栏 insets(用于描述窗口中被系统 UI（如状态栏、导航栏、刘海屏等）占据的空间)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fruitName = intent.getStringExtra(FRUIT_NAME) ?: ""
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val collapsingToolbar = findViewById<com.google.android.material.appbar.CollapsingToolbarLayout>(R.id.collapsingToolbar)
        val fruitImageView = findViewById<android.widget.ImageView>(R.id.fruitImageView)
        val fruitContentText = findViewById<android.widget.TextView>(R.id.fruitContentText)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruitImageView)
        fruitContentText.text = generateFruitContent(fruitName)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun generateFruitContent(fruitName: String) = fruitName.repeat(500)

}