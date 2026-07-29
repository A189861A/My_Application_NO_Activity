package com.example.app5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

class SupervisorScopeSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            launch {
                delay(100)
                println("launch1")
            }
            /*
            * supervisorScope: 该作用域的特点就是抛出的异常不会连锁取消同级协程和父协程
            * */
            supervisorScope {
                launch {
                    delay(500)
                    println("launch2")
//                    throw Exception("--failed--")
                }
                launch {
                    delay(600)
                    println("launch3")
                }
            }
            println("done")
        }
        println("over")
    }
}