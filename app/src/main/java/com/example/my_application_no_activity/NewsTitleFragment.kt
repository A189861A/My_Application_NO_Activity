package com.example.my_application_no_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsTitleFragment : Fragment() {
    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_title_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The 'view' parameter is the root of the fragment's layout.
        // We can search from there. This is safer than using 'activity'.
        isTwoPane = view.findViewById<View>(R.id.newsContentLayout) != null

        val layoutManager = LinearLayoutManager(requireContext())
        val newsTitleRecyclerView: RecyclerView =
            view.findViewById(R.id.newsTitleRecyclerView)

        newsTitleRecyclerView.layoutManager = layoutManager
        val adapter = NewsAdapter(getNews())
        newsTitleRecyclerView.adapter = adapter
    }

    private fun getNews(): List<News> {
        val newsList = ArrayList<News>()
        for (i in 1..50) {
            val news = News(
                "This is news title $i",
                getRandomLengthString(
                    "This is news content $i."
                )
            )
            newsList.add(news)
        }
        return newsList
    }

    private fun getRandomLengthString(str: String): String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }

    inner class NewsAdapter(val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val newsTitle: TextView = view.findViewById(R.id.newsTitle)
            // 在 ViewHolder 初始化时设置点击事件
            init {
                itemView.setOnClickListener {
                    // 确保 holder.adapterPosition 是有效的
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val news = newsList[position]
                        if (isTwoPane) {
                            // 如果是双页模式，则刷新 NewsContentFragment 中的内容
                            val newsContentFrag =
                                requireActivity().supportFragmentManager.findFragmentById(R.id.newsContentFrag)
                            if (newsContentFrag is NewsContentFragment) {
                                newsContentFrag.refresh(news.title, news.content)
                            }
                        } else {
                            // 如果是单页模式，则使用 itemView.context 启动 Activity
                            NewsContentActivity.actionStart(
                                itemView.context, news.title,
                                news.content
                            )
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
//            现在 ViewHolder 自身处理点击事件
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]
            holder.newsTitle.text = news.title
        }

        override fun getItemCount() = newsList.size
    }
}