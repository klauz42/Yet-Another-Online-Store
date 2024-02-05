package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.databinding.CardViewProductBinding
import ru.klauz42.yetanotheronlinestore.di.scopes.FragmentScope
import javax.inject.Inject


@FragmentScope
class ProductsAdapter @Inject constructor() : RecyclerView.Adapter<ProductViewHolder>() {

    interface FavoriteCheckListener {
        fun onChecked(checkBoxView: CompoundButton, id: String, isFavorite: Boolean)
    }

    interface Listener {
        fun itemClickListener(id: String)
        fun favoriteCheckListener(id: String, isFavorite: Boolean)
    }

    private var listener: Listener? = null
    fun setAdapterListener(listener: Listener?) {
        this.listener = listener
    }

    private val items = mutableListOf<ProductWithImages>()
    private val favoriteIds = mutableListOf<String>()

    fun submitList(newItems: List<ProductWithImages>) {
        val result = calculateDiff(items, newItems)

        items.clear()
        items.addAll(newItems)

        result.dispatchUpdatesTo(this)
    }

    fun updateFavoriteIds(newIds: List<String>) {
        val oldSet = favoriteIds.toSet()
        val newSet = newIds.toSet()

        val oldUniques = oldSet.subtract(newSet)
        val newUniques = newSet.subtract(oldSet)
        val idsToUpdate = oldUniques.union(newUniques)

        favoriteIds.clear()
        favoriteIds.addAll(newIds)

        items.forEachIndexed { index, productWithImages ->
            if (idsToUpdate.contains(productWithImages.product.id))
                notifyItemChanged(index)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardViewProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]

        listener?.let {
            holder.bind(
                item,
                favoriteIds.contains(item.product.id),
                it::itemClickListener,
                it::favoriteCheckListener
            )
        }
    }

    private fun calculateDiff(
        items: List<ProductWithImages>,
        newItems: List<ProductWithImages>
    ) = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize() = items.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return items[oldItemPosition].product.id == newItems[newItemPosition].product.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newItem = newItems[newItemPosition]
            val oldItem = items[oldItemPosition]
            return newItem == oldItem
        }
    }
    )
}