package com.example.app3

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//      将一个自定义的 Toolbar（工具栏）设置为当前 Activity 的 ActionBar（应用栏）。
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.toolbar, menu)
            return true
    }
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        Log.d("--onOptionsItemSelected--", "onOptionsItemSelected:${item.itemId}")
        when (item.itemId) {
            R.id.action_search -> {
                // Handle search action
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            }
            R.id.action_settings -> {
                // Handle settings action
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.action_share -> {
                // Handle share action
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}