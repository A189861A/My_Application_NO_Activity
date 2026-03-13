package com.example.app3

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity8 : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main8)

        videoView = findViewById(R.id.videoView)
        playButton = findViewById(R.id.playButton)

        playButton.setOnClickListener {
            playVideo()
        }
    }

    private fun playVideo() {
        // 获取视频资源
        /*
        * Uri: 统一资源标识符，用于标识资源的位置和类型。
        * Uri.parse()：将字符串解析为 Uri 对象。
        * R.raw.movie：视频资源的 ID。
        * */
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.movie}")
        Log.d("videoUri", "videoUri: $packageName")
        // 设置视频路径（raw / assets / 网络地址）
        videoView.setVideoURI(videoUri)
        // 监听视频播放完成
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true  // 循环
            mediaPlayer.start()
        }
        // 监听视频播放完成
        videoView.setOnCompletionListener {
            playButton.text = "重新播放"
        }
        // 监听视频播放错误
        videoView.setOnErrorListener { _, what, extra ->
            playButton.text = "播放失败"
            true
        }
    }
}
