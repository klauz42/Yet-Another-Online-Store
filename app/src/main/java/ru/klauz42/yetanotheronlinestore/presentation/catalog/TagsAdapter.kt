package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.di.scopes.FragmentScope
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.TagCheckBoxItem
import javax.inject.Inject


@FragmentScope
class TagsAdapter @Inject constructor() : RecyclerView.Adapter<TagViewHolder>() {
    interface Listener {
        fun onTagSelected(id: FilterTag, isSelected: Boolean)
    }

    private var listener: Listener? = null
    fun setAdapterListener(listener: Listener?) {
        this.listener = listener
    }

    private val items = mutableListOf<TagCheckBoxItem>()


    fun submitList(newItems: List<TagCheckBoxItem>) {
        val result = calculateDiff(items, newItems)
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = items[position]

        listener?.let {
            holder.bind(item, it::onTagSelected)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isSelected) {
            R.layout.recycler_view_item_selected_tag
        } else {
            R.layout.recycler_view_item_default_tag
        }
    }

    private fun calculateDiff(
        items: List<TagCheckBoxItem>,
        newItems: List<TagCheckBoxItem>
    ) = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize() = items.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return items[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val newItem = newItems[newItemPosition]
            val oldItem = items[oldItemPosition]
            return newItem == oldItem
        }
    }
    )
}