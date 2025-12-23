package com.example.my_application_no_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.my_application_no_activity.R

class NewsContentFragment : Fragment() {

    private lateinit var contentLayout: View
    private lateinit var newsTitle: TextView
    private lateinit var newsContent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_content_frag, container, false)

        // 初始化视图组件
        contentLayout = view.findViewById(R.id.contentLayout)
        newsTitle = view.findViewById(R.id.newsTitle)
        newsContent = view.findViewById(R.id.newsContent)

        return view
    }

    fun refresh(title: String, content: String) {
        contentLayout.visibility = View.VISIBLE
        newsTitle.text = title // 刷新新闻的标题
        newsContent.text = content // 刷新新闻的内容
    }
}