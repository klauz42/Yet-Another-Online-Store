package ru.klauz42.yetanotheronlinestore.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import ru.klauz42.yetanotheronlinestore.databinding.FragmentFavoritesBinding
import ru.klauz42.yetanotheronlinestore.di.components.DaggerFragmentComponent
import ru.klauz42.yetanotheronlinestore.di.components.FragmentComponent
import ru.klauz42.yetanotheronlinestore.domain.models.entities.Product
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import ru.klauz42.yetanotheronlinestore.presentation.ProductMarginItemDecorator
import ru.klauz42.yetanotheronlinestore.presentation.ProductWithImages
import ru.klauz42.yetanotheronlinestore.presentation.ProductsAdapter
import javax.inject.Inject


class FavoritesFragment
    : Fragment(),
    ProductsAdapter.Listener {

    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!

    private lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FavoritesViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent =
            DaggerFragmentComponent.builder()
                .activityComponent((requireActivity() as MainActivity).activityComponent).build()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProductsRecyclerView()

        viewModel.favoriteIds.observe(viewLifecycleOwner) {
            updateLikes(it)
        }

        viewModel.productsLiveData.observe(viewLifecycleOwner) { items ->
            submitProductsAdapterList(items)
        }

        binding.header.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        //todo: fix hardcode
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.content.recyclerViewProducts.visibility = View.VISIBLE
                    }

                    1 -> {
                        binding.content.recyclerViewProducts.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
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

    private fun navigateToProduct(id: String) {
        val action =
            FavoritesFragmentDirections.actionFavoritesToProduct(id)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    override fun itemClickListener(id: String) {
        navigateToProduct(id)
    }

    override fun favoriteCheckListener(id: String, isFavorite: Boolean) {
        viewModel.updateLikedStatus(id, isFavorite)
    }
}