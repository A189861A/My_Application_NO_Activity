package com.example.app5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis
import kotlin.uuid.Uuid

class CoroutineBuilderSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        *launch 函数的方法签名。launch 是一个作用于 CoroutineScope 的扩展函数，
        * 用于在不阻塞当前线程的情况下启动一个协程，
        * 并返回对该协程任务的引用，即 Job 对象
        public fun CoroutineScope.launch(
            context: CoroutineContext = EmptyCoroutineContext,
            start: CoroutineStart = CoroutineStart.DEFAULT,
            block: suspend CoroutineScope.() -> Unit
        ): Job
        * 1. context。用于指定协程的上下文.
        * 2. start。用于指定协程启动模式,默认值为 CoroutineStart.DEFAULT.
        * 3. block。协程体,即希望交由协程执行的任务.
        * 4. 使用 launch 或 async 创建的每个协程都会返回一个 Job 实例.
        *
        * launchA 和 launchB 是并行交叉执行的
        * */
        val time0 = measureTimeMillis {
            runBlocking {
                val launchA = launch {
                    repeat(3) {
                        delay(100)
                        println("launchA - $it")
                    }
                }
                val launchB = launch {
                    repeat(3) {
                        delay(200)
                        println("launchB - $it")
                    }
                }
            }
        }
        // 总耗时 ≈ 600ms（取最长）
        println("time0 = $time0")

        val time = measureTimeMillis {
            runBlocking {
                /*
                * async 可以返回协程的执行结果，而 launch 不行。
                * async 是并发执行的。总耗时基本等于耗时最长的协程.
                * 通过await()方法可以拿到 async 协程的执行结果。
                  ┌────────┬───────────────┬───────────────────────┐
                  │        │    launch     │         async         │
                  ├────────┼───────────────┼───────────────────────┤
                  │ 返回值  │ Job（无结果） │ Deferred<T>（有结果）     │
                  ├────────┼───────────────┼───────────────────────┤
                  │ 用途    │ "发射后不管"  │ 需要拿返回值              │
                  ├────────┼───────────────┼───────────────────────┤
                  │ 并发    │ ✅  是         │ ✅  是               │
                  └────────┴───────────────┴───────────────────────┘
                * */
                val asyncA = async {
                    delay(3000)
                    1
                }
                val asyncB = async {
                    delay(4000)
                    2
                }
                println("total=" + asyncA.await() + asyncB.await())

            }
        }
        // 总耗时 ≈ 4000ms（取最长），不是 7000ms
        println("time = $time")

        CoroutineScope(Dispatchers.IO).launch {
            val time2 = measureTimeMillis {
                val deferreds = listOf(
                    async {
                        delay(1000)
                        1
                    },
                    async {
                        delay(3000)
                        2
                    }
                )
                /*
                * 用于并发执行多个异步任务，并等待它们全部完成.
                * awaitAll 返回一个包含所有异步任务结果的列表（List<T>），其中结果的顺序与传入的 Deferred 顺序严格一致。
                * */
                val ret = deferreds.awaitAll()
                println("ret=${ret[0] + ret[1]}") // 3
            }
            println("time2 = $time2") // 总耗时 ≈ 3000ms（取最长）
        }

        // CoroutineName 用于为协程指定一个名字，方便调试和定位问题
        runBlocking<Unit>(CoroutineName("RunBlocking")) {
            /*
            * withContext: 创建一个指定在 IO 线程池中运行的代码块.
            * */
            withContext(Dispatchers.IO) {
                println("withContext")
            }
        }
        /*
        * 由于 withContext 方法本身就是一个挂起函数，因此 get 方法也必须定义为挂起函数
        * */
        suspend fun get() {
            withContext(Dispatchers.IO) {

            }
        }
    }
}