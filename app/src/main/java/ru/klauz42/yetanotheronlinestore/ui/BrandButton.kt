package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.BrandButtonBinding


class BrandButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: BrandButtonBinding

    var brandName: String? = null
        set(value) {
            field = value
            binding.brandName.text = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.brand_button, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BrandButton, 0, 0)
            val name = typedArray.getString(R.styleable.BrandButton_brandName)
            brandName = name
            typedArray.recycle()
        }
    }
}