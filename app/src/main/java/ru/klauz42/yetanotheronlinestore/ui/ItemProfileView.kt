package ru.klauz42.yetanotheronlinestore.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.ItemProfileBinding


class ItemProfileView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ItemProfileBinding

    var itemTitle: String? = null
        set(value) {
            field = value
            binding.itemTitle.text = value
        }

    var itemSubtitle: String? = null
        set(value) {
            field = value
            binding.itemSubtitle.text = value
        }

    var drawableResId: Int = 0
        set(value) {
            field = value
            if (value != 0) {
                binding.itemIcon.setImageResource(value)
            }
        }

    var isSubtitleVisible: Boolean = false
        set(value) {
            field = value
            binding.itemSubtitle.visibility = if (value) VISIBLE else GONE
        }


    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.item_profile, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ItemProfileView, 0, 0)
            val title = typedArray.getString(R.styleable.ItemProfileView_title)
            val subtitle = typedArray.getString(R.styleable.ItemProfileView_subtitle)
            val drawableResId =
                typedArray.getResourceId(R.styleable.ItemProfileView_drawableStart, 0)
            val subtitleVisible =
                typedArray.getBoolean(R.styleable.ItemProfileView_isSubtitleVisible, false)

            this.itemTitle = title
            this.itemSubtitle = subtitle
            this.drawableResId = drawableResId
            this.isSubtitleVisible = subtitleVisible

            typedArray.recycle()
        }
    }
}