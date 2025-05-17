package com.generals.todoapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.generals.todoapp.R
import com.generals.todoapp.ui.adapter.VP2Adapter
import com.generals.todoapp.ui.fragment.MallFragment
import com.generals.todoapp.ui.fragment.TaskFragment
import com.generals.todoapp.viewmodel.CoinViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CoinActivity : BaseActivity() {

    private val viewModel: CoinViewModel by viewModels()
    var userId = 0
    var coin = 0

    private lateinit var mTvCoin: TextView
    private lateinit var mTabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin)

        userId = intent?.getIntExtra("userId",0)!!

        initView()
        viewModel.getUser(userId)
        initEvent()

        viewModel.livedataUpdate.observe(this) {
            viewModel.getUser(userId)
        }
        viewModel.livedataUser.observe(this) { user ->
            coin = user.coin
            mTvCoin.text = coin.toString()
        }
    }

    private fun initView() {
        mTvCoin = findViewById(R.id.tv_small_coin)
        mTabLayout = findViewById(R.id.tab_coin)
        viewPager2 = findViewById(R.id.vp2_coin)
    }

    private fun initEvent() {
        mTabLayout.addTab(mTabLayout.newTab().setText("积分商店"))
        mTabLayout.addTab(mTabLayout.newTab().setText("积分任务"))
        val fragmentList: List<() -> Fragment> = listOf(
            { MallFragment() },
            { TaskFragment() }
        )
        viewPager2.adapter = VP2Adapter(this,fragmentList)

        TabLayoutMediator(mTabLayout, viewPager2) { tab, position ->
            if(position == 0) {
                tab.text = "积分商城"
            }
            if(position == 1) {
                tab.text = "积分任务"
            }
        }.attach()
    }
}