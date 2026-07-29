package com.example.app5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RunBlockingSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * runBlocking: 只有当内部相同作用域的所有协程都运行结束后，
        * 声明在 runBlocking 之后的代码才能执行，即 runBlocking 会阻塞其所在线程.
        *
        * */
        runBlocking {
            launch {
                repeat(3) {
                    delay(100L)
                    println("launchA $it")
                }
            }

            launch {
                repeat(3) {
                    delay(100L)
                    println("launchB $it")
                }
            }

            GlobalScope.launch {
                repeat(3) {
                    delay(100L)
                    println("GlobalScope $it")
                }
            }
        }
        println("end")

        /*
        * 执行结果：
        * launchA 0
        * launchB 0
        * GlobalScope 0
        *
        * launchA 1
        * launchB 1
        * GlobalScope 1
        *
        * launchA 2
        * launchB 2
        * end
        * GlobalScope 2
        * */


        GlobalScope.launch(Dispatchers.IO) {
            delay(600)
            println("GlobalScope")
        }
        // runBlocking 是一个普通函数,会阻塞当前线程
        runBlocking {
            delay(500)
            println("runBlocking")
            // coroutineScope 是一个协程作用域构建器(挂起函数)
            coroutineScope {
                launch {
                    delay(100)
                    println("coroutineScope")
                }
            }
        }

        //主动休眠两百毫秒，使得和 runBlocking 加起来的延迟时间多于六百毫秒
        Thread.sleep(200)
        println("after sleep")
        /*
        * 执行结果：
        * runBlocking
        * GlobalScope
        * coroutineScope
        * after sleep
        * */

    }
}

