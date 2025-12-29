package com.example.my_application_no_activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

        // 调试信息
        val activity = requireActivity()
        val displayMetrics = activity.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        Log.d("--D--", "屏幕宽度: ${screenWidthDp}dp")
        Log.d("--D--", "Activity 类型: ${activity::class.java.simpleName}")

        // 延迟检测：使用 post 在视图层次完全构建后执行
        view.post {
            detectTwoPaneMode(activity)
        }
    }

    private fun detectTwoPaneMode(activity: FragmentActivity) {
        // 检测双面板模式：在 Activity 的布局中查找 newsContentLayout
        try {
            val ContentLayout = activity.findViewById<View>(R.id.newsContentLayout);
            val hasContentLayout = ContentLayout != null
            Log.d("--D--", "延迟查找 newsContentLayout: $ContentLayout")
            isTwoPane = hasContentLayout

            // 额外检查：查找根布局
            val rootView = activity.findViewById<View>(R.id.newsTitleLayout)
            Log.d("--D--", "延迟查找 根布局类型: ${rootView?.javaClass?.simpleName}")

            hasContentLayout
        } catch (e: IllegalStateException) {
            // Fragment 没有依附到 Activity，默认为单面板模式
            Log.e("--D--", "Fragment 未依附到 Activity", e)
            false
        }

        // 备用检测：基于屏幕宽度的检测
      /*  if (!isTwoPane) {
            val screenWidthDp = activity.resources.displayMetrics.let {
                it.widthPixels / it.density
            }
            // 如果屏幕宽度 >= 600dp 但布局检测失败，强制设为双面板
            if (screenWidthDp >= 600) {
                isTwoPane = true
                Log.d("--D--", "备用检测：强制设置为双面板模式")
            }
        }*/

        Log.d("--D--", "isTwoPane = $isTwoPane")

        // 初始化 RecyclerView
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val view = this.view ?: return
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