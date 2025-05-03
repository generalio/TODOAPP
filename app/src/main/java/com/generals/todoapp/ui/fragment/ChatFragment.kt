package com.generals.todoapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.Chat
import com.generals.todoapp.model.bean.ChatRequest
import com.generals.todoapp.model.bean.Message
import com.generals.todoapp.ui.activity.MainActivity
import com.generals.todoapp.ui.adapter.ChatRecyclerViewAdapter
import com.generals.todoapp.viewmodel.ChatViewModel
import com.google.gson.Gson

class ChatFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    private lateinit var recyclerView: RecyclerView
    private lateinit var mEtContent: TextView
    private lateinit var mBtnClick: Button
    private lateinit var mBtnNoClick: Button
    private lateinit var adapter: ChatRecyclerViewAdapter

    private val viewModel : ChatViewModel by viewModels()

    private var id = 0

    private val chatList: MutableList<Chat> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        recyclerView = view.findViewById(R.id.rv_chat)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        adapter = ChatRecyclerViewAdapter()
        recyclerView.adapter = adapter
        mEtContent = view.findViewById(R.id.et_chat)
        mBtnClick = view.findViewById(R.id.btn_chat)
        mBtnNoClick = view.findViewById(R.id.btn_chat_no_click)
        mBtnNoClick.visibility = View.GONE
        mBtnClick.visibility = View.VISIBLE

        initEvent()

        viewModel.livedataChatResponse.observe(viewLifecycleOwner) { response ->
            var text = ""
            if(response.code != 200) {
                text = response.msg
            } else {
                text = response.content
            }
            loadChat(text)
            mBtnNoClick.visibility = View.GONE
            mBtnClick.visibility = View.VISIBLE
        }
    }

    private fun initEvent() {
        mBtnClick.setOnClickListener {
            val text = mEtContent.text.toString()
            loadChat(text)
            val list : MutableList<Message> = mutableListOf()
            list.add(Message(text,"user"))
            viewModel.chat(ChatRequest(list, "qwq-32b"))
            mBtnClick.visibility = View.GONE
            mBtnNoClick.visibility = View.VISIBLE
            mEtContent.text = ""
        }
    }

    private fun loadChat(text: String) {
        id++
        val chat = Chat(text, id % 2, id)
        chatList.add(chat)
        adapter.submitList(chatList.toList())
        recyclerView.smoothScrollToPosition(id - 1)
    }
}