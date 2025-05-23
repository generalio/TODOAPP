package com.generals.todoapp.ui.custom

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

    private var lastX = 0 //上一次滑动的x,y
    private var lastY = 0
    private var initialX = 0 //初始按下那一瞬间的x

    private lateinit var textLayout: View
    private lateinit var topLayout: View
    private lateinit var deleteLayout: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        textLayout = findViewById(R.id.parent_text_layout)
        topLayout = findViewById(R.id.parent_top)
        deleteLayout = findViewById(R.id.parent_delete)
    }

//    private var click : (() -> Unit)? = null
//    // 点击接口
//    fun setOnCustomClickListener(cb: () -> Unit) {
//        click = cb
//    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        /**
         * 点击事件初始化必须在这里写
         * 因为onMove()收不到点击事件，被子View拦截了
         */
        when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = ev.x.toInt() - topLayout.translationX.toInt() //计算偏移量
                lastX = ev.x.toInt()
                lastY = ev.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                if(abs(ev.x - lastX) > slopTouch || abs(ev.y - lastY) > slopTouch) {
                    return true //由父View消费滑动事件
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
            // 处理滑动事件
            /**
             * 这里实现的是两个按钮同时显现出来（最好在view里面设置一下elevation控制一下高度）
             * 前面的view移动的距离为transitionX / 2(因为只占显示的一半)
             * 后面的view移动距离为transitionX / 2 - view.width(因为初始位置在屏幕外view.width的距离，故要先移到贴近屏幕的位置再进行移动一半的操作)
             * 这里是-view.width是因为左移是负的
             */
            MotionEvent.ACTION_MOVE -> {
                val transitionX = (event.x - initialX).coerceIn(-(topLayout.width + deleteLayout.width).toFloat(), 0F)
                textLayout.translationX = transitionX
                topLayout.translationX = transitionX
                deleteLayout.translationX = transitionX / 2 - deleteLayout.width
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
//                // 点击完成，执行回调(只能在ACTION_UP内执行)
//                click?.invoke()
//                // 为了支持无障碍、焦点等，调用 performClick()
//                performClick()
            }
        }
        return true
    }

//    override fun performClick(): Boolean {
//        super.performClick()
//        return true
//    }

}