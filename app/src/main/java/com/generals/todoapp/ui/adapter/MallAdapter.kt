package com.generals.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R

/**
 * @Desc : Mall的Adapter
 * @Author : zzx
 * @Date : 2025/5/4 23:22
 */

class MallAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MallAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun click()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btn : Button = view.findViewById(R.id.btn_mall)
        init {
           btn.setOnClickListener {
               itemClickListener.click()
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_mall, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 18
    }

}