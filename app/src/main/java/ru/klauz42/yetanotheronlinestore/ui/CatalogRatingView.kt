package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.CatalogRatingViewBinding


class CatalogRatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CatalogRatingViewBinding

    var ratingValue: Float = 0.0f
        set(value) {
            field = value
            binding.ratingValue.text = String.format("%.1f ", value)
        }
    var feedbackCount: Int = 0
        set(value) {
            field = value
            binding.feedbackCount.text = String.format("(%d)", value)
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.catalog_rating_view, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CatalogRatingView, 0, 0)
            val value = typedArray.getFloat(R.styleable.CatalogRatingView_ratingValue, 0.0f)
            val count = typedArray.getInt(R.styleable.CatalogRatingView_feedbackCount, 0)
            ratingValue = value
            feedbackCount = count
            typedArray.recycle()
        }
    }
}