package com.generals.todoapp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.generals.todoapp.R
import com.generals.todoapp.ui.adapter.VP2Adapter
import com.generals.todoapp.ui.fragment.ChatFragment
import com.generals.todoapp.ui.fragment.HomeFragment
import com.generals.todoapp.ui.fragment.PersonFragment
import com.generals.todoapp.ui.fragment.TimeFragment
import com.generals.todoapp.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity() {

    private lateinit var navigationView: BottomNavigationView
    private lateinit var viewPager2: ViewPager2

    private val viewmodel : MainViewModel by viewModels()

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initEvent()

        val userId = intent.getIntExtra("userId",-1)
        if(userId != -1) {
            viewmodel.setUser(userId)
        }

    }

    private fun initView() {
        navigationView = findViewById(R.id.navigation_main)
        viewPager2 = findViewById(R.id.vp2_main)
    }

    private fun initEvent() {
        handler = Handler(Looper.getMainLooper()) //调换了handler位置，防止不开始计时导致Handler未初始化
        setNavigation()
    }

    private fun setNavigation() {
        //将fragment添加进适配器
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(HomeFragment())
        fragmentList.add(TimeFragment())
        fragmentList.add(ChatFragment())
        fragmentList.add(PersonFragment())
        viewPager2.adapter = VP2Adapter(this, fragmentList)
        viewPager2.isUserInputEnabled = false
        navigationView.selectedItemId = R.id.bottom_home
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.bottom_home -> {
                    viewPager2.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_time -> {
                    viewPager2.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_chat -> {
                    viewPager2.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_person -> {
                    viewPager2.currentItem = 3
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigationView.menu.getItem(position).isChecked = true
            }
        })
    }

    fun showInfo(text: String) {
        text.showToast()
    }

    fun countDown(times: Int) {
        var count = times
        runnable = object : Runnable {
            override fun run() {
                viewmodel.sendTime(count - 1)
                count--
                if(count > 0) {
                    handler.postDelayed(this, 1000)
                } else {
                    "计时已完成!".showToast()
                }
            }
        }
        handler.postDelayed(runnable, 1000)
    }

    fun stopCountDown() {
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}