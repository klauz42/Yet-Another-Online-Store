package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import ru.klauz42.yetanotheronlinestore.databinding.CardViewProductBinding


class ProductViewHolder(private val binding: CardViewProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: ProductWithImages,
        isLiked: Boolean,
        itemClickListener: (String) -> Unit,
        checkBoxListener: (String, Boolean) -> Unit,
    ) {
        binding.apply {
            productItem = item
            this.checkBoxListener = object : ProductsAdapter.FavoriteCheckListener {
                override fun onChecked(
                    checkBoxView: CompoundButton,
                    id: String,
                    isFavorite: Boolean
                ) {
                    if (checkBoxView.isPressed)
                        checkBoxListener(item.product.id, isFavorite)
                }
            }

            checkboxLike.isChecked = isLiked

            val imageCarouselAdapter = ImageCarouselAdapter(item.imagesResId) {
                itemClickListener(item.product.id)
            }
            viewPager.adapter = imageCarouselAdapter

            TabLayoutMediator(tabDots, viewPager) { tab, position ->

            }.attach()

            itemView.setOnClickListener {
                itemClickListener(item.product.id)
            }
        }

    }
}