package ru.klauz42.yetanotheronlinestore.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.databinding.FragmentProfileBinding
import ru.klauz42.yetanotheronlinestore.di.components.DaggerFragmentComponent
import ru.klauz42.yetanotheronlinestore.di.components.FragmentComponent
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import ru.klauz42.yetanotheronlinestore.presentation.getPluralFormText
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var fragmentComponent: FragmentComponent

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProfileViewModel by viewModels { viewModelFactory }

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = view.context

        viewModel.userData.observe(viewLifecycleOwner) { userData ->
            binding.userCard.apply {
                userName = userData.firstName + " " + userData.secondName
                userPhoneNumber = userData.phoneNumber
            }
        }

        val form1 = context.getString(R.string.products_one)
        val form2 = context.getString(R.string.products_few)
        val form5 = context.getString(R.string.products_many)

        viewModel.favoritesCount.observe(viewLifecycleOwner) {
            binding.favorites.itemSubtitle = getPluralFormText(it, form1, form2, form5)
        }

        binding.favorites.setOnClickListener {
            navigateToFavorites()
        }

        binding.buttonSignOut.setOnClickListener {
            (requireActivity() as MainActivity).signOut()
        }
    }

    private fun navigateToFavorites() {
        val action =
            ProfileFragmentDirections.actionProfileToFavorites()
        findNavController().navigate(action)
    }
}