package com.generals.todoapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.ChildTask
import com.generals.todoapp.model.bean.ParentTask
import com.generals.todoapp.ui.CustomDialog
import com.generals.todoapp.ui.activity.MainActivity
import com.generals.todoapp.ui.adapter.HomeTaskRecyclerViewAdapter
import com.generals.todoapp.viewmodel.HomeViewModel
import com.generals.todoapp.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(), HomeTaskRecyclerViewAdapter.OnItemClickListener {

    var userId : Int = 0

    private val viewModel : HomeViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var mainActivity : MainActivity

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatButton: FloatingActionButton
    private lateinit var adapter: HomeTaskRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        mainViewModel.livedataUser.observe(viewLifecycleOwner) { user ->
            userId = user.id
            loadPassage()
        }

        recyclerView = view.findViewById(R.id.rv_home)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.addItemDecoration(DividerItemDecoration(mainActivity, RecyclerView.VERTICAL))
        floatButton = view.findViewById(R.id.home_floatButton)
        adapter = HomeTaskRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

        initEvent()

        viewModel.livedataIsChanged.observe(viewLifecycleOwner) {
            loadPassage()
        }

        val list : MutableList<ParentTask> = mutableListOf()
        val parentList : MutableList<ParentTask> = mutableListOf()
        viewModel.livedataParentTask.observe(viewLifecycleOwner) { parentTask ->
            parentList.clear()
            parentList.addAll(parentTask)
            viewModel.loadAllChildTask(1, userId)
        }

        viewModel.livedataChildTask.observe(viewLifecycleOwner) { childTask ->
            list.clear()
            for(parent in parentList) {
                list.add(parent)
                for(child in childTask) {
                    if(child.parentId == parent.id) {
                        val newChildTask = ParentTask(child.title,"",userId, child.parentId * 1000 + child.id)
                        newChildTask.grade = 2
                        newChildTask.parentId = child.parentId
                        if(parent.finish == 1) {
                            newChildTask.finish = 1
                        }
                        if(parent.top == 0) {
                            newChildTask.top = 0
                        }
                        list.add(newChildTask)
                    }
                }
            }
            adapter.submitList(list.toList())
        }

    }


    @SuppressLint("InflateParams")
    private fun initEvent() {
        floatButton.setOnClickListener {
            val editView = LayoutInflater.from(mainActivity).inflate(R.layout.layout_edit, null)!!
            val dialog = CustomDialog()
                .newInstance()
                .setDialogHeight(1000)
                .setCustomView(editView)
            val ft: FragmentTransaction =
                mainActivity.supportFragmentManager.beginTransaction()
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //设置过渡动画
            dialog.show(ft, "DialogMore") //开启bottomSheetDialog

            val mEtTitle : EditText = editView.findViewById(R.id.et_edit_title)
            val mEtDesc : EditText = editView.findViewById(R.id.et_edit_desc)
            val mEtChildTitle : EditText = editView.findViewById(R.id.et_edit_child)
            mEtChildTitle.visibility = View.GONE
            val mBtnConfirm : Button = editView.findViewById(R.id.btn_edit_confirm)

            mBtnConfirm.setOnClickListener {
                if(mEtTitle.text.toString() == "") {
                    Toast.makeText(context, "内容不能为空" , Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertParentTask(ParentTask(mEtTitle.text.toString(), mEtDesc.text.toString(), userId))
                    dialog.dismiss()
                }
            }
        }
    }

    private fun loadPassage() {
        viewModel.loadAllParentTask(userId)
    }

    override fun onItemViewClick(parentTask: ParentTask, type: Int) {
        val editView = LayoutInflater.from(mainActivity).inflate(R.layout.layout_edit, null)!!
        val dialog = CustomDialog()
            .newInstance()
            .setDialogHeight(1000)
            .setCustomView(editView)
        val ft: FragmentTransaction =
            mainActivity.supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //设置过渡动画
        dialog.show(ft, "DialogMore") //开启bottomSheetDialog

        val mEtTitle : EditText = editView.findViewById(R.id.et_edit_title)
        val mEtDesc : EditText = editView.findViewById(R.id.et_edit_desc)
        val mEtChildTitle : EditText = editView.findViewById(R.id.et_edit_child)
        val mBtnConfirm : Button = editView.findViewById(R.id.btn_edit_confirm)

        val title = parentTask.title
        val desc = parentTask.desc

        mEtTitle.setText(parentTask.title)
        if(type == 1) {
            mEtChildTitle.visibility = View.VISIBLE
            mEtDesc.setText(parentTask.desc)
            mBtnConfirm.setOnClickListener {
                val childTitle = mEtChildTitle.text.toString()
                if(childTitle != "") {
                    viewModel.insertChildTask(ChildTask(childTitle,parentTask.id, userId))
                }
                if(title != mEtTitle.text.toString() || desc != mEtDesc.text.toString()) {
                    val updateTask = ParentTask(mEtTitle.text.toString(), mEtDesc.text.toString(), userId, parentTask.id)
                    updateTask.finish = parentTask.finish
                    updateTask.top = parentTask.top
                    viewModel.updateParentTask(updateTask)
                }
                dialog.dismiss()
            }
        } else {
            mEtChildTitle.visibility = View.GONE
            mEtDesc.visibility = View.GONE
            mBtnConfirm.setOnClickListener {
                if(title != mEtTitle.text.toString()) {
                    viewModel.updateChildTask(ChildTask(mEtTitle.text.toString(), parentTask.parentId, userId, parentTask.id - (1000 * parentTask.parentId)))
                }
                dialog.dismiss()
            }
        }
    }

    override fun onCheckStatusChanged(parentTask: ParentTask) {
        viewModel.finishParentTask(parentTask.id)
    }

    override fun onParentDelete(parentTask: ParentTask) {
        viewModel.deleteParentTask(parentTask)
    }

    override fun onChildDelete(childTask: ParentTask) {
        val child = ChildTask(childTask.title, childTask.parentId, userId, childTask.id - (1000 * childTask.parentId))
        viewModel.deleteChildTask(child)
    }

    override fun onParentTop(parentTask: ParentTask) {
        viewModel.updateTopStatus(parentTask.id)
    }

}