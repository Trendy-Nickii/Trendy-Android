package com.kh.ite.rupp.edu.trendy.Ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.kh.ite.rupp.edu.trendy.R

class CustomCheckbox(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var isChecked: Boolean = false
    private var checkColor: Int = 0xFF000000.toInt()
    private var boxColor: Int = 0xFFFFFFFF.toInt()
    private var checkPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var boxPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var boxRect: RectF = RectF()

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomCheckbox, 0, 0)
        try {
            isChecked = a.getBoolean(R.styleable.CustomCheckbox_checked, false)
            checkColor = a.getColor(R.styleable.CustomCheckbox_checkColor, 0xFF000000.toInt())
            boxColor = a.getColor(R.styleable.CustomCheckbox_boxColor, 0xFFFFFFFF.toInt())
        } finally {
            a.recycle()
        }

        checkPaint.style = Paint.Style.STROKE
        checkPaint.strokeWidth = 5f
        checkPaint.color = checkColor

        boxPaint.style = Paint.Style.FILL
        boxPaint.color = boxColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2f) - 5f

        boxRect.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        canvas.drawRoundRect(boxRect, 10f, 10f, boxPaint)

        if (isChecked) {
            canvas.drawLine(
                centerX - radius / 2f, centerY, centerX, centerY + radius / 2f, checkPaint
            )
            canvas.drawLine(
                centerX, centerY + radius / 2f, centerX + radius, centerY - radius / 2f, checkPaint
            )
        }
    }

    fun isChecked(): Boolean {
        return isChecked
    }

    fun setChecked(checked: Boolean) {
        isChecked = checked
        invalidate()
        requestLayout()
    }

    fun toggle() {
        isChecked = !isChecked
        invalidate()
        requestLayout()
    }
}
