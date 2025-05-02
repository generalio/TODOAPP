package com.generals.todoapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import com.generals.todoapp.R
import kotlin.math.abs

/**
 * @Desc : 侧滑LinearLayout
 * @Author : zzx
 * @Date : 2025/5/2 16:51
 */

class CustomParentLinearLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val slopTouch = ViewConfiguration.get(context).scaledTouchSlop

    private var lastX = 0
    private var lastY = 0
    private var initialX = 0

    private lateinit var textLayout: View
    private lateinit var topLayout: View
    private lateinit var deleteLayout: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        textLayout = findViewById(R.id.parent_text_layout)
        topLayout = findViewById(R.id.parent_top)
        deleteLayout = findViewById(R.id.parent_delete)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = ev.x.toInt() - topLayout.translationX.toInt()
                lastX = ev.x.toInt()
                lastY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                if(abs(ev.x - lastX) > slopTouch || abs(ev.y - lastY) > slopTouch) {
                    return true
                }
                lastX = ev.x.toInt()
                lastY = ev.y.toInt()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_MOVE -> {
                val transitionX = (event.x - initialX).coerceIn(-(topLayout.width + deleteLayout.width).toFloat(), 0F)
                textLayout.translationX = transitionX
                topLayout.translationX = transitionX
                deleteLayout.translationX = transitionX
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
                if(-topLayout.translationX > (topLayout.width + deleteLayout.width) / 2) {
                    textLayout.animate().translationX(-(topLayout.width + deleteLayout.width).toFloat())
                    topLayout.animate().translationX(-(topLayout.width + deleteLayout.width).toFloat())
                    deleteLayout.animate().translationX(-(topLayout.width + deleteLayout.width).toFloat())
                } else {
                    textLayout.animate().translationX(0F)
                    topLayout.animate().translationX(0F)
                    deleteLayout.animate().translationX(0F)
                }
            }
        }
        return true
    }

}