package com.generals.todoapp.ui.fragment

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import com.generals.todoapp.R
import com.generals.todoapp.ui.activity.CoinActivity
import com.generals.todoapp.viewmodel.CoinViewModel

class TaskFragment : Fragment() {

    private val coinViewModel: CoinViewModel by activityViewModels()
    private lateinit var coinActivity: CoinActivity

    private lateinit var mBtnQiandao: Button
    private lateinit var mBtnFinish: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinActivity = activity as CoinActivity
        mBtnQiandao = view.findViewById(R.id.btn_qiandao)
        mBtnFinish = view.findViewById(R.id.btn_finish)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var isQian = false
        val sharedPreferences = coinActivity.getSharedPreferences("date", Context.MODE_PRIVATE)

        val getYear = sharedPreferences.getInt("year",0)
        val getMonth = sharedPreferences.getInt("month",0)
        val getDay = sharedPreferences.getInt("day",0)
        if(year == getYear && month == getMonth && day == getDay) {
            isQian = true
            mBtnQiandao.visibility = View.GONE
            mBtnFinish.visibility = View.VISIBLE
        } else {
            isQian = false
            mBtnQiandao.visibility = View.VISIBLE
            mBtnFinish.visibility = View.GONE
        }
        mBtnQiandao.setOnClickListener {
            coinViewModel.updateCoin(coinActivity.userId, coinActivity.coin + 10)
            mBtnQiandao.visibility = View.GONE
            mBtnFinish.visibility = View.VISIBLE
            isQian = true
            sharedPreferences.edit {
                putInt("year", year)
                putInt("month", month)
                putInt("day", day)
            }
        }

    }
}