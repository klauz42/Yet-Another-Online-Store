package ru.klauz42.yetanotheronlinestore.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.R

class ProductMarginItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources = view.context.resources
        val margin = resources.getDimensionPixelSize(R.dimen.catalog_product_rv_item_margin)

        outRect.top = margin
        outRect.bottom = margin
        outRect.right = margin
        outRect.left = margin
    }
}