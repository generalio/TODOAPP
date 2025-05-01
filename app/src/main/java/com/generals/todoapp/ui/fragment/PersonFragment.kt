package com.generals.todoapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.generals.todoapp.R
import com.generals.todoapp.viewmodel.MainViewModel

class PersonFragment : Fragment() {

    private lateinit var mTvUsername: TextView
    private lateinit var mTvCoin: TextView

    private val mainViewModel : MainViewModel by activityViewModels()

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
        mTvCoin = view.findViewById(R.id.tv_person_coin)
        initEvent()

    }

    private fun initEvent() {
        mainViewModel.livedataUser.observe(viewLifecycleOwner) { user ->
            mTvUsername.text = user.username
            mTvCoin.text = user.coin.toString()
        }
    }

}