package com.example.my_application_no_activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_application_no_activity.R
import entity.Msg
import entity.MsgAdapter

class MainActivity5 : AppCompatActivity(), View.OnClickListener {
    private val msgList = ArrayList<Msg>()
    private var adapter: MsgAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var inputText: EditText
    private lateinit var sendBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)

        initMsg()

        // 初始化视图组件
//        findViewById 应该在 Activity 的根视图中查找所有组件
        recyclerView = findViewById(R.id.recyclerView5)
        inputText = findViewById(R.id.inputText)
        sendBtn = findViewById(R.id.send)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = MsgAdapter(msgList)
        recyclerView.adapter = adapter

        // 设置点击监听
        sendBtn.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Msg(content, Msg.TYPE_SENT)
                    msgList.add(msg)
                    /*
                    * notifyItemInserted 方法用于通知适配器，在指定位置插入新项
                    * */
                    adapter?.notifyItemInserted(msgList.size - 1)
                    /*
                    * scrollToPosition 方法用于将 RecyclerView 滚动到指定位置
                    * */
                    recyclerView.scrollToPosition(msgList.size - 1)
                    inputText.setText("")
                }
            }
        }
    }

    private fun initMsg() {
        val msg1 = Msg("Hello guy.", Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello. Who is that?", Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}