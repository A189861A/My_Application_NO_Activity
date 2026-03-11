package com.example.app3.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

/*
 * SimpleWorker：简单的后台任务 Worker 类
 *   - 继承自 Worker 类，用于在后台线程执行任务
 *   - 构造函数：接收 Context 和 WorkerParameters 参数
 *   - doWork()：必须实现的方法，用于执行后台任务
 *   - 返回 Result.success() 表示任务成功，Result.failure() 表示任务失败
 * */
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    // doWork是Worker类唯一的构造函数
    // doWork不会运行在主线程当中，在这里执行耗时逻辑
    override fun doWork(): Result {
        // 具体的后台任务逻辑
        Log.d("SimpleWorker", "Background task started")
        
        // 模拟后台任务
        try {
            Thread.sleep(3000)
            Log.d("SimpleWorker", "Background task completed")
            return Result.success()
        } catch (e: InterruptedException) {
            Log.e("SimpleWorker", "Background task failed", e)
            return Result.failure()
        }
    }
}