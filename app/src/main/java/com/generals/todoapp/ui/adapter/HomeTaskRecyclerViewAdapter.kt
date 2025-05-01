package com.generals.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.ParentTask

/**
 * @Desc : Task的Adapter
 * @Author : zzx
 * @Date : 2025/5/1 22:29
 */

class HomeTaskRecyclerViewAdapter() : ListAdapter<ParentTask, RecyclerView.ViewHolder>( object : DiffUtil.ItemCallback<ParentTask>() {
    override fun areContentsTheSame(oldItem: ParentTask, newItem: ParentTask): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ParentTask, newItem: ParentTask): Boolean {
        return oldItem == newItem //考虑到二级菜单的id问题，直接粗暴解决()
    }

}) {

    inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox : CheckBox = view.findViewById(R.id.item_parent_check)
        val parentTitle : TextView = view.findViewById(R.id.tv_parent_title)
        val parentDesc : TextView = view.findViewById(R.id.tv_parent_desc)
        val expand : ImageView = view.findViewById(R.id.iv_parent_expand)
    }

    inner class  ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val childTitle : TextView = view.findViewById(R.id.tv_child_title)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)
        when(holder) {
            is ParentViewHolder -> {
                holder.parentTitle.text = task.title
                holder.parentDesc.text = task.desc
                if(task.expand == 0) {
                    holder.expand.setImageResource(R.drawable.ic_folder)
                } else {
                    holder.expand.setImageResource(R.drawable.ic_expand)
                }
                if(task.finish == 0) {
                    holder.checkBox.isChecked = false
                } else {
                    holder.checkBox.isChecked = true
                }
                if(task.desc == "") {
                    holder.parentDesc.visibility = View.GONE
                } else {
                    holder.parentDesc.visibility = View.VISIBLE
                }
            }

            is ChildViewHolder -> {
                holder.childTitle.text = task.title
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