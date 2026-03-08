package com.example.app3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app3.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv4.setOnClickListener {
            binding.tv4.text = "TextView clicked!"
        }

    }
}