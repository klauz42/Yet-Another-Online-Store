package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.DiscountLabelBinding


class DiscountLabel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: DiscountLabelBinding

    var discountValue: Int = 0
        set(value) {
            field = value
            binding.discountValue.text = context.getString(R.string.discount_label, value)
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.discount_label, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DiscountLabel, 0, 0)
            val value = typedArray.getInt(R.styleable.DiscountLabel_discountValue, 0)
            discountValue = value
            typedArray.recycle()
        }
    }
}