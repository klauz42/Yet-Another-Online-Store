package ru.klauz42.yetanotheronlinestore.presentation.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.FragmentCatalogBinding
import ru.klauz42.yetanotheronlinestore.di.components.DaggerFragmentComponent
import ru.klauz42.yetanotheronlinestore.di.components.FragmentComponent
import ru.klauz42.yetanotheronlinestore.domain.models.entities.FilterTag
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import ru.klauz42.yetanotheronlinestore.domain.models.entities.SortType
import ru.klauz42.yetanotheronlinestore.domain.models.entities.TagCheckBoxItem
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import ru.klauz42.yetanotheronlinestore.presentation.ProductWithImages
import javax.inject.Inject


class CatalogFragment
    : Fragment(),
    TagsAdapter.Listener,
    ProductsAdapter.Listener {

    private var _binding: FragmentCatalogBinding? = null
    val binding get() = _binding!!

    private lateinit var fragmentComponent: FragmentComponent
    private val activity: MainActivity by lazy { requireActivity() as MainActivity }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CatalogViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var tagsAdapter: TagsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent =
            DaggerFragmentComponent.builder().activityComponent(activity.activityComponent).build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sortType.observe(viewLifecycleOwner) {
            binding.buttonSortBy.title = getString(it.getStringResId())
        }

        viewModel.favoriteIds.observe(viewLifecycleOwner) {
            updateLikes(it)
        }

        binding.buttonSortBy.setOnClickListener { v ->
            val menu = PopupMenu(context, v)
            menu.inflate(R.menu.menu_sort_by)
            menu.setOnMenuItemClickListener { menuItem ->
                menuItem.itemId.toSortType()?.let { sortType ->
                    viewModel.setSortType(sortType)
                }
                binding.buttonSortBy.title = menuItem.title.toString()
                true
            }
            menu.show()
        }

        setupTagsRecyclerView()
        setupProductsRecyclerView()

        viewModel.productsLiveData.observe(viewLifecycleOwner) { items ->
            submitProductsAdapterList(items)
        }

        viewModel.tagListLiveData.observe(viewLifecycleOwner) {
            submitTagsAdapterList(it)
        }

    }

    private fun submitTagsAdapterList(itemList: List<TagCheckBoxItem>) {
        (binding.content.recyclerViewTags.adapter as TagsAdapter).submitList(itemList)
    }

    private fun submitProductsAdapterList(itemList: List<Product>) {

        val productsWithImages: List<ProductWithImages> = itemList.map { product ->
            ProductWithImages.from(product)
        }

        (binding.content.recyclerViewProducts.adapter as ProductsAdapter).submitList(
            productsWithImages
        )
    }

    private fun updateLikes(favoriteIds: List<String>) {
        (binding.content.recyclerViewProducts.adapter as ProductsAdapter).updateFavoriteIds(
            favoriteIds
        )
    }


    private fun setupTagsRecyclerView() {
        tagsAdapter.setAdapterListener(this)

        binding.content.recyclerViewTags.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            addItemDecoration(TagMarginItemDecorator())
            adapter = tagsAdapter
        }
    }

    private val reuseViewHolderItemAnimator = object : DefaultItemAnimator() {
        override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = true
    }

    private fun setupProductsRecyclerView() {
        productsAdapter.setAdapterListener(this)

        binding.content.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(ProductMarginItemDecorator())
            adapter = productsAdapter
            itemAnimator = reuseViewHolderItemAnimator
        }
    }

    private fun SortType.getStringResId(): Int {
        return when (this) {
            SortType.BY_RATING -> R.string.by_rating
            SortType.PRICE_DESCENDING -> R.string.descending_price
            SortType.PRICE_ASCENDING -> R.string.ascending_price
        }
    }

    private fun navigateToProduct(id: String) {
        val action =
            CatalogFragmentDirections.actionCatalogToProduct(id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    private fun Int.toSortType(): SortType? {
        return when (this) {
            R.id.option_by_rating -> SortType.BY_RATING
            R.id.option_descending_price -> SortType.PRICE_DESCENDING
            R.id.option_ascending_price -> SortType.PRICE_ASCENDING
            else -> null
        }
    }

    override fun itemClickListener(id: String) {
        navigateToProduct(id)
    }

    override fun favoriteCheckListener(id: String, isFavorite: Boolean) {
        viewModel.updateLikedStatus(id, isFavorite)
    }

    override fun onTagSelected(id: FilterTag, isSelected: Boolean) {
        viewModel.updateTag(id, isSelected)
    }
}