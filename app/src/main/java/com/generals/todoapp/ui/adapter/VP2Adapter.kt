package com.generals.todoapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Desc : VP2的Adapter
 * @Author : zzx
 * @Date : 2025/5/1 11:57
 */

class VP2Adapter(fragmentActivity: FragmentActivity, private val list: List<() -> Fragment>) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]()
    }
}