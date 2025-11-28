package com.example.my_application_no_activity

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third_layout)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // 1. 获取Intent中的HTTPS链接
        val intent = intent
        val httpsUri: Uri? = intent.data

        if (httpsUri != null && httpsUri.scheme == "https") {
            // 2. 初始化WebView并加载HTTPS链接
            val webView: WebView = findViewById(R.id.webView)
            setupWebView(webView)
            webView.loadUrl(httpsUri.toString())
        } else {
            // 无有效HTTPS链接时提示并关闭Activity
            Toast.makeText(this, "无效的HTTPS链接", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    /**
     * 配置WebView支持HTTPS加载、JavaScript等
     */
    private fun setupWebView(webView: WebView) {
        val webSettings = webView.settings
        // 允许JavaScript（部分HTTPS页面需要）
        webSettings.javaScriptEnabled = true
        // 启用DOM存储（避免页面加载异常）
        webSettings.domStorageEnabled = true
        // 允许混合内容（若HTTPS页面包含HTTP资源，可选开启）
        webSettings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // 关键：开启WebView调试模式
        WebView.setWebContentsDebuggingEnabled(true)



        // 设置WebViewClient，避免跳转到系统浏览器
        webView.webViewClient = object : WebViewClient() {
            // 拦截页面跳转（可选，确保所有链接在当前WebView打开）
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
    }


}