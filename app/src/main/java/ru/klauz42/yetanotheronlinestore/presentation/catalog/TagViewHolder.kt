package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.TagCheckBoxItem


class TagViewHolder(private val itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(
        item: TagCheckBoxItem,
        checkBoxCallback: (id: FilterTag, isSelected: Boolean) -> Unit,
    ) {
        val title: TextView = itemView.findViewById(R.id.title)
        title.text = item.text
        itemView.setOnClickListener {
            checkBoxCallback(item.id, !item.isSelected)
        }
    }
}