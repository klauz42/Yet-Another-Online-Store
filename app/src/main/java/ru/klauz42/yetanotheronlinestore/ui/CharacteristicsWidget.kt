package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.CharacteristicsWidgetBinding

class CharacteristicsWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CharacteristicsWidgetBinding

    var productCode: String? = null
        set(value) {
            field = value
            binding.productCode.text = value
        }

    var applicationArea: String? = null
        set(value) {
            field = value
            binding.applicationArea.text = value
        }

    var countryOfOrigin: String? = null
        set(value) {
            field = value
            binding.countryOfOrigin.text = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.characteristics_widget, this, true)

        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.CharacteristicsWidget, 0, 0)
            val productCode = typedArray.getString(R.styleable.CharacteristicsWidget_productCode)
            val applicationArea =
                typedArray.getString(R.styleable.CharacteristicsWidget_applicationArea)
            val countryOfOrigin =
                typedArray.getString(R.styleable.CharacteristicsWidget_countryOfOrigin)
            this.productCode = productCode
            this.applicationArea = applicationArea
            this.countryOfOrigin = countryOfOrigin
            typedArray.recycle()
        }
    }
}