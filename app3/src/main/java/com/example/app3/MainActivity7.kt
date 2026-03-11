package com.example.app3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.app3.entity.User
import com.example.app3.model.UserViewModel2
import com.example.app3.worker.SimpleWorker
import kotlinx.coroutines.launch

class MainActivity7 : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        viewModel = ViewModelProvider(this)[UserViewModel2::class.java]

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        
        button2.setOnClickListener {
            // 1.插入测试数据
            val testUser = User(null, "张三", 18, "zhangsan@example.com")
            viewModel.insertUser(testUser)
        }
        button.setOnClickListener {
            // 观察用户数据变化（自动更新UI）
            /*
            * lifecycleScope:
            *   用于管理协程的生命周期，确保协程在 Activity/Fragment 销毁时自动取消。
            *   提供的一个协程作用域构建器，它绑定到 LifecycleOwner（如 Activity、Fragment）的生命周期。
            *  - 默认在 Dispatchers.Main（主线程）
            *  - 使用 withContext(Dispatchers.IO) 切换到 IO 线程执行耗时操作
            *  - 执行完后自动返回主线程
            *  非阻塞：launch 是非阻塞的，onCreate 会继续执行后续代码.
            *  lifecycleScope.launch: 启动lifecycleScope协程（默认主线程）
            * */
            lifecycleScope.launch {
                /*
                * Flow: 用于异步流数据，可以处理大量数据而不阻塞线程。
                *   collect:
                *       - 会持续监听，数据库数据变化时自动回调.
                *       - 订阅并处理 Flow 发射的每一个数据，必须在协程中调用.
                * */
                viewModel.allUsers.collect { users ->
                    // 数据变化时更新UI，比如显示到 RecyclerView
                    Log.d("--users--", "用户列表：${users.size}")
                    if (users.isEmpty()) {
                        println("用户列表为空")
                    } else {
                        users.forEach { user ->
                            println("用户：${user.name}，年龄：${user.age}")
                        }
                    }
                }
            }
        }
        // worker 测试
        button3.setOnClickListener {
            startWorkerWithPendingIntent()
        }
    }
    
    private fun startWorkerWithPendingIntent() {
        // 创建通知渠道（Android 8.0+ 需要）
        createNotificationChannel()
        
        // 创建 PendingIntent
        /*
        * flags: Intent 的标志位属性，用于控制 Activity 的启动行为
        * Intent.FLAG_ACTIVITY_NEW_TASK: 在新的任务栈中启动 Activity
        * Intent.FLAG_ACTIVITY_CLEAR_TASK: 清除任务栈中的所有 Activity，并将新的 Activity 放入任务栈的顶部
        * Intent.FLAG_ACTIVITY_SINGLE_TOP: 如果 Activity 已经在栈顶，则不创建新的实例，而是调用 onNewIntent() 方法
        * Intent.FLAG_ACTIVITY_MULTIPLE_TASK: 允许多个实例同时启动
        * FLAG_ACTIVITY_CLEAR_TOP: 如果 Activity 已经在栈顶，则清除栈顶之上的所有 Activity，并将新的 Activity 放入栈顶
        * or 按位或操作符，同时应用两个标志
        *
        * */
        val intent = Intent(this, MainActivity7::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        // Android 12+ 需要指定 FLAG_IMMUTABLE 或 FLAG_MUTABLE
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        /*
        * PendingIntent = "延迟执行的 Intent",它是一个意图的包装器，允许其他应用在未来某个时间点以你的应用身份执行特定操作。
        * getActivity() 方法：创建一个 PendingIntent，用于启动指定的 Activity
        * */
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            pendingIntentFlags
        )
        
        // 创建 WorkRequest
        /*
        * OneTimeWorkRequestBuilder<SimpleWorker>()：创建一个一次性的 WorkRequest，用于执行 SimpleWorker 类中的 doWork() 方法。
        * build()：构建 WorkRequest 对象，准备提交给 WorkManager。
        * */
        val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
            .build()
        
        // 获取 WorkManager 实例并提交任务
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(workRequest)
        
        // 显示通知
        showNotification(pendingIntent)
        
        Log.d("MainActivity7", "WorkRequest 已提交，ID: ${workRequest.id}")
    }
    /*
    *
    * 创建通知渠道（Android 8.0+ 需要）,用于分类管理通知，让用户可以更好地控制不同类型的通知.
    * */
    private fun createNotificationChannel() {
        // 检查当前版本是否大于等于 Android 8.0
        // uild.VERSION_CODES.O = Android 8.0 (Oreo) 的版本代号常量
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "worker_channel",
                "Worker 通知",
                NotificationManager.IMPORTANCE_DEFAULT // 通知重要性级别
            ).apply {
                description = "后台任务通知渠道"
            }
            // 获取管理器并注册通知渠道
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun showNotification(pendingIntent: PendingIntent) {
        /*
        * 创建通知（NotificationCompat.Builder）：用于构建通知对象，设置通知的图标、标题、内容、点击意图等。
        * setSmallIcon()：设置通知的小图标，通常是应用图标或相关图标。
        * setContentTitle()：设置通知的标题，用于显示在通知列表中。
        * setContentText()：设置通知的详细内容，用于显示在通知详情中。
        * setContentIntent()：设置点击通知时要启动的 PendingIntent，通常是启动应用的主 Activity。
        * setAutoCancel()：设置点击通知后是否自动取消通知，通常设置为 true 以确保用户点击后能够清除通知。
        * build()：构建 Notification 对象，准备显示通知。
        * */
        val notification = NotificationCompat.Builder(this, "worker_channel")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentTitle("后台任务")
            .setContentText("后台任务已启动")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        /*
        * 创建通知管理器（NotificationManager）
        * 显示通知（NotificationManager.notify()）：将构建好的通知对象显示在通知栏中。
        * 第一个参数：通知的唯一标识符，用于后续更新或取消通知。
        * 第二个参数：要显示的 Notification 对象。
        *
        * getSystemService()：获取系统服务，这里获取的是通知管理器（NotificationManager）。
        * */
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification)
    }
}