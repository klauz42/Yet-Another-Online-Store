package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.klauz42.yetanotheronlinestore.R

class ImageCarouselAdapter(
    private val images: List<Int>,
    private val onClickListener: () -> Unit
) :
    RecyclerView.Adapter<ImageCarouselAdapter.ViewHolder>() {

    inner class ViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        init {
            itemView.setOnClickListener { onClickListener() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false) as ImageView
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(images[position]).into(holder.imageView)
    }

    override fun getItemCount() = images.size


}