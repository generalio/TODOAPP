package com.generals.todoapp.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.generals.todoapp.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/5 13:16
 */

class CustomMallLinearLayout(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet), NestedScrollingParent2  {

    private lateinit var cardView: MaterialCardView
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private var maxSlopHeight = 0F

    override fun onFinishInflate() {
        super.onFinishInflate()
        cardView = findViewById(R.id.card_coin)
        tabLayout = findViewById(R.id.tab_coin)
        viewPager2 = findViewById(R.id.vp2_coin)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        maxSlopHeight = cardView.measuredHeight.toFloat() //得到cardview的高
    }


//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        when(ev.action) {
//            MotionEvent.ACTION_DOWN -> {
//                initialY = ev.y.toInt() - cardView.translationY.toInt()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                if(abs(initialY - ev.y) >= maxSlopHeight) {
//                    return false
//                } else {
//                    return true
//                }
//            }
//        }
//        return super.onInterceptTouchEvent(ev)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when(event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                initialY = event.y.toInt() - cardView.translationY.toInt()
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val transitionY = (event.y - initialY).coerceIn(-maxSlopHeight, 0F)
//                cardView.translationY = transitionY
//                tabLayout.translationY = transitionY
//                viewPager2.translationY = transitionY
//            }
//            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
//                if(-cardView.translationY > maxSlopHeight / 2) {
//                    cardView.animate().translationY(-maxSlopHeight)
//                    tabLayout.animate().translationY(-maxSlopHeight)
//                    viewPager2.animate().translationY(-maxSlopHeight)
//                } else {
//                    cardView.animate().translationY(0F)
//                    tabLayout.animate().translationY(0F)
//                    viewPager2.animate().translationY(0F)
//                }
//            }
//        }
//        return super.onTouchEvent(event)
//    }

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false //可以删
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?) = false //可以删

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0 //如果检测到垂直滑动就由自己处理，否则就不处理
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    override fun onStopNestedScroll(target: View, type: Int) {
        super.onStopNestedScroll(target)
        /**
         * 当结束嵌套滑动时
         * 判断距离是否>1/2*height
         * 是就吸顶，否就回原位
         */
        if(-cardView.translationY > maxSlopHeight / 2) {
            autoMove(-maxSlopHeight)
        } else {
            autoMove(0F)
        }
    }

    //手势下滑时，先把rv拉到顶部再拉积分详情
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if(dyUnconsumed < 0 && cardView.translationY < 0) {
            val consume = max(dyUnconsumed.toFloat(), cardView.translationY)
            move(-consume)
        }
    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        var consume = 0F //初始化父view需要消耗的距离
        if(dy > 0 && -cardView.translationY < maxSlopHeight ) {
            consume = min(dy.toFloat(), cardView.translationY + maxSlopHeight) //对剩余滑的距离(cardview高度-已经滑的高度)和当前滑动距离取最小值
            move(-consume) //向上滑是负的，而dy是向上滑为正
        }
//        if(dy < 0 && cardView.translationY < 0) {
//            consume = max(dy.toFloat(), cardView.translationY) //同理，这是向下滑时
//            move(-consume)
//        }
        consumed[1] = consume.toInt() //consume[1]是y轴的消费距离
    }

    private fun move(transition: Float) {
        // 这里不能用animate().translationY(),因为这个是启动个动画，而我们需要下一帧就立即执行，因为如果这一帧没有消费位移，就会认为父没有消费从而交给子View消费
        val transitionY = (cardView.translationY + transition).coerceIn(-maxSlopHeight, 0F)
        cardView.translationY = transitionY
        tabLayout.translationY = transitionY
        viewPager2.translationY = transitionY
    }

    private fun autoMove(transition: Float) {
        cardView.animate().translationY(transition)
        tabLayout.animate().translationY(transition)
        viewPager2.animate().translationY(transition)

    }

}