package com.generals.todoapp.ui.custom

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @Desc : 倒计时的圆
 * @Author : zzx
 * @Date : 2025/5/3 23:06
 */

@SuppressLint("Recycle")
class CustomCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dst = Path()
    private val circlePath = Path()
    private var circlePathMeasure: PathMeasure
    private var mCurAnimValue = 0F
    private var animator: ValueAnimator

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.style =Paint.Style.STROKE
        paint.strokeWidth = 50F
        paint.color = Color.argb(255,254,247,255)
        val cx = 710F //圆心坐标
        val cy = 710F
        val r  = 500F //半径
        val oval = RectF(cx - r, cy - r, cx + r, cy + r) //以这个矩形画圆（两个对角）
        circlePath.addArc(oval, -90F, -360F) //以上方为起点，逆时针画360°
        circlePathMeasure = PathMeasure(circlePath, true)
        animator = ValueAnimator.ofFloat(0F,1F)
        animator.addUpdateListener { animation ->
            mCurAnimValue = animation.animatedValue as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //路径动画
        val stop = circlePathMeasure.length * mCurAnimValue
        dst.reset()
        circlePathMeasure.getSegment(0F,stop,dst,true)
        canvas.drawPath(dst, paint)
    }

    fun startAnimate(times: Int) {
        animator.duration = (times * 1000 * 2).toLong()
        animator.interpolator = LinearInterpolator()
        animator.start()
    }

//    fun pauseAnimate() {
//        animator.pause()
//    }
//
//    fun continueAnimate() {
//        animator.resume()
//    }

    fun resetAnimate() {
        animate().cancel()
    }

}