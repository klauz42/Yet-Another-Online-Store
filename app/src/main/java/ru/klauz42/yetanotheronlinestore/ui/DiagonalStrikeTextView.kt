package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.klauz42.yetanotheronlinestore.R


class DiagonalStrikeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val paintLine: Paint = Paint().apply {
        color = currentTextColor
        strokeWidth = resources.getDimension(R.dimen.strikethrough_line_width)
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val text = text.toString()
        val textWidth = paint.measureText(text)
        val textHeight = with(paint.fontMetrics) { descent - ascent }

        val startX = paddingStart.toFloat()
        val startY = paddingTop.toFloat() + textHeight * 0.8f

        val endX = startX + textWidth
        val endY = paddingTop.toFloat() + textHeight * 0.5f

        canvas.drawLine(startX, startY, endX, endY, paintLine)
    }
}