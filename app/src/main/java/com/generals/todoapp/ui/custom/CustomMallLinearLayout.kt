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
        maxSlopHeight = cardView.measuredHeight.toFloat()
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

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?) = false

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    override fun onStopNestedScroll(target: View, type: Int) {
        super.onStopNestedScroll(target)
        if(-cardView.translationY > maxSlopHeight / 2) {
            autoMove(-maxSlopHeight)
        } else {
            autoMove(0F)
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        var consume = 0F
        if(dy > 0 && -cardView.translationY < maxSlopHeight ) {
            consume = min(dy.toFloat(), cardView.translationY + maxSlopHeight)
            move(-consume)
        }
        if(dy < 0 && cardView.translationY < 0) {
            consume = max(dy.toFloat(), cardView.translationY)
            move(-consume)
        }
        consumed[1] = consume.toInt()
    }

    private fun move(transition: Float) {
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