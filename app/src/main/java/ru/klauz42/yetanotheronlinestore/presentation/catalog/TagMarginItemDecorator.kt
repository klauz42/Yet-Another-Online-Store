package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.R

class TagMarginItemDecorator : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources = view.context.resources
        val margin = resources.getDimensionPixelSize(R.dimen.catalog_tag_rv_item_margin)

        val position = parent.getChildAdapterPosition(view)

        if (position != 0) {
            if (ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_LTR) {
                outRect.left = margin
            } else {
                outRect.right = margin
            }
        }
    }
}