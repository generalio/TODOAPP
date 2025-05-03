package com.generals.todoapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.generals.todoapp.R
import com.generals.todoapp.ui.activity.MainActivity
import com.generals.todoapp.ui.custom.CustomCircle
import com.generals.todoapp.viewmodel.MainViewModel
import kotlin.math.min

class TimeFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    private lateinit var mTvTime: TextView
    private lateinit var mBtnStart: Button
    private lateinit var mBtnPause: Button
    private lateinit var mBtnStop: Button
    private lateinit var mBtnSet: Button
    private lateinit var mBtnContinue: Button
    private lateinit var mEtHour: EditText
    private lateinit var mEtMinute: EditText
    private lateinit var mEtSecond: EditText
    private lateinit var mCustomCircle: CustomCircle

    var times = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvTime = view.findViewById(R.id.tv_time)
        mBtnStart = view.findViewById(R.id.btn_time_start)
//        mBtnPause = view.findViewById(R.id.btn_time_pause)
        mBtnStop = view.findViewById(R.id.btn_time_stop)
        mBtnSet = view.findViewById(R.id.btn_time_set)
//        mBtnContinue = view.findViewById(R.id.btn_time_continue)
        mEtHour = view.findViewById(R.id.et_hour)
        mEtMinute = view.findViewById(R.id.et_minute)
        mEtSecond = view.findViewById(R.id.et_second)
        mCustomCircle = view.findViewById(R.id.custom_circle)

        mainActivity = activity as MainActivity

        mainViewModel.livedataCount.observe(viewLifecycleOwner) {
            times = it
            setTimes()
            if(times == 0) {
                initView()
            }
        }
        if(times != 0) {
            startView()
        } else {
            initView()
        }
        hideSet()
        initEvent()

    }

    private fun setTimes() {
        val hour = times / 3600
        val minute = (times - hour * 3600) / 60
        val second = times - hour * 3600 - minute * 60
        var hourText = hour.toString()
        var minuteText = minute.toString()
        var secondText = second.toString()
        if(hour < 10) {
            hourText = "0$hour"
        }
        if(minute < 10) {
            minuteText = "0$minute"
        }
        if(second < 10) {
            secondText = "0$second"
        }
        mTvTime.text = "${hourText}:${minuteText}:${secondText}"
    }

    private fun initEvent() {
        mBtnSet.setOnClickListener {
            showSet()
            mBtnSet.visibility = View.GONE
            mBtnStop.visibility = View.GONE
        }
        mBtnStart.setOnClickListener {
            var hour = 0
            var minute = 0
            var second = 0
            if(mEtHour.text.toString() != "") {
                hour = mEtHour.text.toString().toInt()
            }
            if(mEtMinute.text.toString() != "") {
                minute = mEtMinute.text.toString().toInt()
            }
            if(mEtSecond.text.toString() != "") {
                second = mEtSecond.text.toString().toInt()
            }
            val count = hour * 3600 + minute * 60 + second
            if(count == 0) {
                mainActivity.showInfo("时间不能为0!")
            } else {
                if(hour >= 0 && hour <= 99 && minute >= 0 && minute <= 59 && second >= 0 && second <= 59) {
                    var hourText = hour.toString()
                    var minuteText = minute.toString()
                    var secondText = second.toString()
                    if(hour < 10) {
                        hourText = "0$hour"
                    }
                    if(minute < 10) {
                        minuteText = "0$minute"
                    }
                    if(second < 10) {
                        secondText = "0$second"
                    }
                    mCustomCircle.visibility = View.VISIBLE
                    mCustomCircle.startAnimate(count)
                    mTvTime.text = "${hourText}:${minuteText}:${secondText}"
                    mEtHour.setText("")
                    mEtMinute.setText("")
                    mEtSecond.setText("")
                    mainActivity.countDown(count)
                    startView()
                    hideSet()
                }
            }
        }
//        mBtnPause.setOnClickListener {
//            mCustomCircle.pauseAnimate()
//            mainActivity.stopCountDown()
//            mBtnContinue.visibility = View.VISIBLE
//            mBtnPause.visibility = View.GONE
//        }
//        mBtnContinue.setOnClickListener {
//            mCustomCircle.continueAnimate()
//            if(times != 0) {
//                mainActivity.countDown(times)
//                mBtnContinue.visibility = View.GONE
//                startView()
//            }
//        }
        mBtnStop.setOnClickListener {
            mCustomCircle.resetAnimate()
            mCustomCircle.visibility = View.GONE
            mainActivity.stopCountDown()
            mTvTime.text = "00:00:00"
            initView()
        }
    }

    private fun initView() {
        mBtnStart.visibility = View.VISIBLE
        mBtnSet.visibility = View.VISIBLE
//        mBtnPause.visibility = View.GONE
        mBtnStop.visibility = View.GONE
//        mBtnContinue.visibility = View.GONE
    }

    private fun startView() {
        mBtnStart.visibility = View.GONE
        mBtnSet.visibility = View.GONE
//        mBtnPause.visibility = View.VISIBLE
        mBtnStop.visibility = View.VISIBLE
    }

    private fun showSet() {
        mEtHour.visibility = View.VISIBLE
        mEtMinute.visibility = View.VISIBLE
        mEtSecond.visibility = View.VISIBLE
    }

    private fun hideSet() {
        mEtHour.visibility = View.GONE
        mEtMinute.visibility = View.GONE
        mEtSecond.visibility = View.GONE
    }

}