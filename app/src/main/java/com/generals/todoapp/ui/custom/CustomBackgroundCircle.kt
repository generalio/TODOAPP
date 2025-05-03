package com.generals.todoapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/4 00:25
 */

class CustomBackgroundCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint()
    val path = Path()

    init {
        paint.style =Paint.Style.STROKE
        paint.strokeWidth = 50F
        paint.color = Color.argb(255,0,142,145)
        val cx = 710F //圆心坐标
        val cy = 710F
        val r  = 500f //半径
        val oval = RectF(cx - r, cy - r, cx + r, cy + r) //以这个矩形画圆（两个对角）
        path.addArc(oval, -90F, -360F) //以上方为起点，逆时针画360°
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

}