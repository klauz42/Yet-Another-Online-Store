package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.AddToCartButtonBinding

class AddToCartButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: AddToCartButtonBinding

    var price: String = ""
        set(value) {
            field = value
            binding.price.text = value
        }

    var priceWithDiscount: String = ""
        set(value) {
            field = value
            binding.priceWithDiscount.text = value
        }


    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.add_to_cart_button, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AddToCartButton, 0, 0)
            val price = typedArray.getString(R.styleable.AddToCartButton_price)
            val priceWithDiscount =
                typedArray.getString(R.styleable.AddToCartButton_priceWithDiscount)
            this.price = price ?: ""
            this.priceWithDiscount = priceWithDiscount ?: ""
            typedArray.recycle()
        }
    }
}