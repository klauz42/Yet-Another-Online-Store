package ru.klauz42.yetanotheronlinestore.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.FragmentProductBinding
import ru.klauz42.yetanotheronlinestore.di.components.DaggerFragmentComponent
import ru.klauz42.yetanotheronlinestore.di.components.FragmentComponent
import ru.klauz42.yetanotheronlinestore.presentation.ImageCarouselAdapter
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import ru.klauz42.yetanotheronlinestore.presentation.getPluralFormText
import javax.inject.Inject

class ProductFragment : Fragment() {

    private lateinit var fragmentComponent: FragmentComponent
    private val activity: MainActivity by lazy { requireActivity() as MainActivity }

    private var _binding: FragmentProductBinding? = null
    val binding get() = _binding!!
    private val args: ProductFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProductViewModel by viewModels { viewModelFactory }

    private var id: String? = null

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
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root

        with(args) {
            id = productId
            viewModel.loadProductItem(productId)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        binding.content.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupHideMoreDescriptionButton()
        setupHideMoreIngredientsButton()

        viewModel.productWithImages.observe(viewLifecycleOwner) {
            it?.let { productWithImages ->
                binding.content.apply {
                    val product = productWithImages.product

                    val availableForm1 = context.getString(R.string.available_one)
                    val availableForm2 = context.getString(R.string.available_few)
                    val availableForm5 = context.getString(R.string.available_many)
                    available.text = getPluralFormText(
                        product.available,
                        availableForm1,
                        availableForm2,
                        availableForm5
                    )

                    buttonHideMoreDescription.text = requireContext().getString(R.string.hide)
                    buttonHideMoreIngredients.text = requireContext().getString(R.string.hide)

                    checkboxLike.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (buttonView.isPressed) {
                            viewModel?.updateLikedStatus(isChecked)
                        }
                    }

                    val imageCarouselAdapter = ImageCarouselAdapter(productWithImages.imagesResId)
                    viewPager.adapter = imageCarouselAdapter

                    TabLayoutMediator(tabDots, viewPager) { tab, position ->

                    }.attach()
                }

                //todo: remove hardcode, rewrite CharacteristicsWidget logic
                val applicationOfArea =
                    productWithImages.product.info.find { it.title == "Область использования" }?.value
                        ?: "-"
                val countryOfOrigin =
                    productWithImages.product.info.find { it.title == "Страна производства" }?.value
                        ?: "-"
                val productCode =
                    productWithImages.product.info.find { it.title == "Артикул товара" }?.value
                        ?: "-"
                binding.content.characteristics.apply {
                    this.applicationArea = applicationOfArea
                    this.countryOfOrigin = countryOfOrigin
                    this.productCode = productCode
                }
            }
        }

        viewModel.isFavoriteLiveData.observe(viewLifecycleOwner) {
            binding.content.checkboxLike.isChecked = it
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }


    private fun setupHideMoreDescriptionButton() {
        val hide = requireContext().getString(R.string.hide)
        val more = requireContext().getString(R.string.more)

        binding.content.apply {
            if (descriptionBlock.visibility == VISIBLE) {
                buttonHideMoreDescription.text = hide
            } else {
                buttonHideMoreDescription.text = more
            }

            buttonHideMoreDescription.setOnClickListener {
                if (descriptionBlock.visibility == VISIBLE) {
                    buttonHideMoreDescription.text = more
                    descriptionBlock.visibility = GONE
                } else {
                    buttonHideMoreDescription.text = hide
                    descriptionBlock.visibility = VISIBLE
                }
            }
        }
    }

    private fun setupHideMoreIngredientsButton() {
        val hide = requireContext().getString(R.string.hide)
        val more = requireContext().getString(R.string.more)

        binding.header.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.content.apply {
            if (listOfIngredients.maxLines != 2) {
                buttonHideMoreIngredients.text = hide
            } else {
                buttonHideMoreIngredients.text = more
            }

            buttonHideMoreIngredients.setOnClickListener {
                if (listOfIngredients.maxLines != 2) {
                    buttonHideMoreIngredients.text = more
                    listOfIngredients.maxLines = 2
                } else {
                    buttonHideMoreIngredients.text = hide
                    listOfIngredients.maxLines = Int.MAX_VALUE
                }
            }
        }
    }
}