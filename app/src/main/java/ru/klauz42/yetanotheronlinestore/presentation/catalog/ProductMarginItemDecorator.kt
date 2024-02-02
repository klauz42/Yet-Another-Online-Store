package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
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
        val spanCount = resources.getInteger(R.integer.catalog_product_rv_item_span_count)
        val margin = resources.getDimensionPixelSize(R.dimen.catalog_product_rv_item_margin)

        val position = parent.getChildAdapterPosition(view)

        if (position >= spanCount) outRect.top = margin

        if (position % spanCount != (spanCount - 1)) {
            if (ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_LTR) {
                outRect.right = margin
            } else {
                outRect.left = margin
            }
        }
    }
}