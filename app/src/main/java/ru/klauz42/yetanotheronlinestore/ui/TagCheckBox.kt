package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import ru.klauz42.yetanotheronlinestore.R

class TagCheckBox(
    context: Context, attrs: AttributeSet
) : AppCompatCheckBox(
    context,
    attrs,
    R.attr.tagCheckBoxStyle,
) {
    init {
        updateDrawableEnd(isChecked)
        setOnCheckedChangeListener { _, isChecked ->
            updateDrawableEnd(isChecked)
        }
    }

    private fun updateDrawableEnd(isChecked: Boolean) {
        val drawableEnd: Drawable?
        val paddingEnd: Int

        if (isChecked) {
            drawableEnd = ContextCompat.getDrawable(context, R.drawable.small_close)
            paddingEnd = resources.getDimensionPixelSize(R.dimen.tag_padding_end)
        } else {
            drawableEnd = null
            paddingEnd = resources.getDimensionPixelSize(R.dimen.tag_padding_start)
        }

        val paddingStart = paddingStart
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableEnd, null)
        setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
    }
}
