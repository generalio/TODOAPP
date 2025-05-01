package com.generals.todoapp.ui.activity

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * @Desc : Activity基类
 * @Author : zzx
 * @Date : 2025/5/1 11:09
 */

open class BaseActivity : AppCompatActivity() {

    private val context: Context = this

    //设置Toast
    fun String.showToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    fun Int.showToast() {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    //设置dialog
    fun String.showDialog(title: String, block: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(this@showDialog)
            setCancelable(false)
            setPositiveButton("确认") { _, which -> block() }
            show()
        }
    }

    fun String.showCancelDialog(title: String, block: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(this@showCancelDialog)
            setPositiveButton("确认") { _, which -> block() }
            setNegativeButton("取消") { dialog, which -> }
            show()
        }
    }

}