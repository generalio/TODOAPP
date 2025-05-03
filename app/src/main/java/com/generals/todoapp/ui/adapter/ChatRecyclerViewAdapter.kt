package com.generals.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.Chat

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/3 10:58
 */

class ChatRecyclerViewAdapter() : ListAdapter<Chat, RecyclerView.ViewHolder>( object : DiffUtil.ItemCallback<Chat>() {
    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.id == newItem.id
    }

} ) {

    inner class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvLeft : TextView = view.findViewById(R.id.tv_left)
    }

    inner class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mTvRight : TextView = view.findViewById(R.id.tv_right)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = getItem(position)
        when(holder) {
            is LeftViewHolder -> {
                holder.mTvLeft.text = HtmlCompat.fromHtml(chat.content , HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            is RightViewHolder -> {
                holder.mTvRight.text = HtmlCompat.fromHtml(chat.content , HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_left, parent, false)
            return LeftViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_right, parent, false)
            return RightViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

}