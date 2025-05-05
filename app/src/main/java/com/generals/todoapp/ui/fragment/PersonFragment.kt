package com.generals.todoapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import com.generals.todoapp.R
import com.generals.todoapp.ui.activity.CoinActivity
import com.generals.todoapp.ui.activity.MainActivity
import com.generals.todoapp.viewmodel.MainViewModel
import kotlin.properties.Delegates

class PersonFragment : Fragment() {

    private lateinit var mTvUsername: TextView
    private lateinit var mBtnCoin: Button
    private lateinit var mBtnLogout: Button

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var mainActivity : MainActivity

    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTvUsername = view.findViewById(R.id.tv_person_username)
        mBtnCoin = view.findViewById(R.id.btn_person_coin)
        mBtnLogout = view.findViewById(R.id.btn_person_logout)
        mainActivity = activity as MainActivity
        initEvent()

    }

    private fun initEvent() {
        mainViewModel.livedataUser.observe(viewLifecycleOwner) { user ->
            mTvUsername.text = user.username
            userId = user.id
        }
        mBtnLogout.setOnClickListener {
            mainActivity.getSharedPreferences("user",Context.MODE_PRIVATE).edit {
                putString("username","")
                putString("password","")
            }
            mainActivity.finish()
        }
        mBtnCoin.setOnClickListener {
            val intent = Intent(mainActivity, CoinActivity::class.java)
            intent.putExtra("userId", userId)
            mainActivity.startActivity(intent)
        }
    }

}