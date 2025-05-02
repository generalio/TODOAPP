package com.generals.todoapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.ChildTask
import com.generals.todoapp.model.bean.ParentTask

/**
 * @Desc : Task的Adapter
 * @Author : zzx
 * @Date : 2025/5/1 22:29
 */

class HomeTaskRecyclerViewAdapter(val itemClickListener: OnItemClickListener) : ListAdapter<ParentTask, RecyclerView.ViewHolder>( object : DiffUtil.ItemCallback<ParentTask>() {
    override fun areContentsTheSame(oldItem: ParentTask, newItem: ParentTask): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ParentTask, newItem: ParentTask): Boolean {
        return oldItem.id == newItem.id && oldItem.finish == newItem.finish && oldItem.top == newItem.top
    }

}) {

    interface OnItemClickListener {
        fun onItemViewClick(parentTask: ParentTask, type: Int)
        fun onCheckStatusChanged(parentTask: ParentTask)
        fun onParentDelete(parentTask: ParentTask)
        fun onChildDelete(childTask: ParentTask)
        fun onParentTop(parentTask: ParentTask)
    }

    inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox : CheckBox = view.findViewById(R.id.item_parent_check)
        val parentTitle : TextView = view.findViewById(R.id.tv_parent_title)
        val parentDesc : TextView = view.findViewById(R.id.tv_parent_desc)
        private val parentDelete : TextView = view.findViewById(R.id.parent_delete)
        val parentTop : TextView = view.findViewById(R.id.parent_top)
        val top : ImageView = view.findViewById(R.id.iv_top)
        val parentLayout : View = view.findViewById(R.id.parent_text_layout)
        init {
            parentLayout.setOnClickListener {
                parentLayout.animate().translationX(0F)
                parentDelete.animate().translationX(0F)
                parentTop.animate().translationX(0F)
                itemClickListener.onItemViewClick(getItem(adapterPosition),1)
            }
            checkBox.setOnCheckedChangeListener { _, isCheck ->
                val task = getItem(adapterPosition)
                if(checkBox.isPressed) {
                    itemClickListener.onCheckStatusChanged(task)
                    if(isCheck) {
                        parentTitle.text = HtmlCompat.fromHtml("<s>${task.title}</s>" , HtmlCompat.FROM_HTML_MODE_LEGACY)
                        parentDesc.text = HtmlCompat.fromHtml("<s>${task.desc}</s>" , HtmlCompat.FROM_HTML_MODE_LEGACY)
                    } else {
                        parentTitle.text = task.title
                        parentDesc.text = task.desc
                    }
                }
            }
            parentDelete.setOnClickListener {
                itemClickListener.onParentDelete(getItem(adapterPosition))
            }
            parentTop.setOnClickListener {
                parentLayout.animate().translationX(0F)
                parentDelete.animate().translationX(0F)
                parentTop.animate().translationX(0F)
                if(getItem(adapterPosition).top == 0) {
                    top.visibility = View.GONE
                    parentTop.text = "置顶"
                    parentLayout.background = null
                } else {
                    top.visibility = View.VISIBLE
                    parentTop.text = "取消置顶"
                    parentLayout.setBackgroundColor(Color.parseColor("#DEDEDE"))
                }
                itemClickListener.onParentTop(getItem(adapterPosition))
            }
        }
    }

    inner class  ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val childTitle : TextView = view.findViewById(R.id.tv_child_title)
        private val childDelete: TextView = view.findViewById(R.id.child_delete)
        val childLayout : View = view.findViewById(R.id.child_layout)
        init {
            childTitle.setOnClickListener {
                itemClickListener.onItemViewClick(getItem(adapterPosition),2)
            }
            childDelete.setOnClickListener {
                itemClickListener.onChildDelete(getItem(adapterPosition))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)
        when(holder) {
            is ParentViewHolder -> {
                if(task.finish == 0) {
                    holder.checkBox.isChecked = false
                    holder.parentTitle.text = task.title
                    holder.parentDesc.text = task.desc
                } else {
                    holder.checkBox.isChecked = true
                    holder.parentTitle.text = HtmlCompat.fromHtml("<s>${task.title}</s>" , HtmlCompat.FROM_HTML_MODE_LEGACY)
                    holder.parentDesc.text = HtmlCompat.fromHtml("<s>${task.desc}</s>" , HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                if(task.desc == "") {
                    holder.parentDesc.visibility = View.GONE
                } else {
                    holder.parentDesc.visibility = View.VISIBLE
                }
                if(task.top == 1) {
                    holder.top.visibility = View.GONE
                    holder.parentTop.text = "置顶"
                    holder.parentLayout.background = null
                } else {
                    holder.top.visibility = View.VISIBLE
                    holder.parentTop.text = "取消置顶"
                    holder.parentLayout.setBackgroundColor(Color.parseColor("#DEDEDE"))
                }
            }

            is ChildViewHolder -> {
                holder.childTitle.text = task.title
                if(task.finish == 1) {
                    holder.childTitle.text = HtmlCompat.fromHtml("<s>${task.title}</s>" , HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                if(task.top == 0) {
                    holder.childLayout.setBackgroundColor(Color.parseColor("#DEDEDE"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parent_task, parent, false)
            return ParentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child_task, parent, false)
            return ChildViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).grade
    }

}