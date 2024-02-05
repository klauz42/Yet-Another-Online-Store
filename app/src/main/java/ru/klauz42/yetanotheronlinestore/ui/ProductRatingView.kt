package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.ProductRatingViewBinding
import ru.klauz42.yetanotheronlinestore.presentation.getPluralFormText


class ProductRatingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ProductRatingViewBinding

    private val form1 = context.getString(R.string.feedbacks_one)
    private val form2 = context.getString(R.string.feedbacks_few)
    private val form5 = context.getString(R.string.feedbacks_many)

    var ratingValue: Float = 0.0f
        set(value) {
            field = value
            binding.ratingValue.text = String.format("%.1f", value)
            binding.ratingStars.rating = value
        }
    var feedbackCount: Int = 0
        set(value) {
            field = value
            binding.feedbackCount.text = getPluralFormText(value, form1, form2, form5)
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.product_rating_view, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProductRatingView, 0, 0)
            val value = typedArray.getFloat(R.styleable.ProductRatingView_ratingValue, 0.0f)
            val count = typedArray.getInt(R.styleable.ProductRatingView_feedbackCount, 0)
            ratingValue = value
            feedbackCount = count
            typedArray.recycle()
        }
    }
}