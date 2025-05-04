package com.generals.todoapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.ui.activity.CoinActivity
import com.generals.todoapp.ui.adapter.MallAdapter
import com.generals.todoapp.viewmodel.CoinViewModel

class MallFragment : Fragment(), MallAdapter.OnItemClickListener {

    private val coinViewModel : CoinViewModel by activityViewModels()
    private lateinit var coinActivity: CoinActivity

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MallAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mall, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinActivity = activity as CoinActivity
        recyclerView = view.findViewById(R.id.rv_mall)
        adapter = MallAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(coinActivity, 2)
        recyclerView.adapter = adapter
    }

    override fun click() {
        coinViewModel.updateCoin(coinActivity.userId, coinActivity.coin - 5)
    }
}