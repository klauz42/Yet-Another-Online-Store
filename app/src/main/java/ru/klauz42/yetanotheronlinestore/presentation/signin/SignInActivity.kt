package ru.klauz42.yetanotheronlinestore.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.appComponent
import ru.klauz42.yetanotheronlinestore.databinding.ActivitySignInBinding
import ru.klauz42.yetanotheronlinestore.di.components.ActivityComponent
import ru.klauz42.yetanotheronlinestore.di.components.DaggerActivityComponent
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    private var _binding: ActivitySignInBinding? = null
    private val binding: ActivitySignInBinding get() = _binding!!

    lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SignInViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent(appComponent)
            .activity(this).build()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)

        _binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()


        viewModel.canSignIn.observe(this) {
            binding.signInButton.isEnabled = it
        }

        viewModel.hasFirstNameError.observe(this) { hasError ->
            binding.firstNameInputLayout.setErrorStroke(hasError)
        }

        viewModel.hasSecondNameError.observe(this) { hasError ->
            binding.secondNameInputLayout.setErrorStroke(hasError)
        }

        viewModel.hasPhoneNumberError.observe(this) { hasError ->
            binding.phoneNumberInputLayout.setErrorStroke(hasError)
        }

        binding.apply {
            linkLoyaltyProgram.apply {
                text = Html.fromHtml(
                    getString(R.string.loyalty_program_link),
                    Html.FROM_HTML_MODE_LEGACY
                )
                isClickable = false
            }

            firstNameEditText.addTextChangedListener {
                viewModel.updateFirstName(it.toString())
            }

            secondNameEditText.addTextChangedListener {
                viewModel.updateSecondName(it.toString())
            }


            phoneNumberEditText.addTextChangedListener(PhoneNumberWatcher {
                viewModel.updatePhoneNumber(it)
            })


            signInButton.setOnClickListener {
                viewModel.saveUserData()
                goToMainActivity()
            }
        }
    }

    private fun TextInputLayout.setErrorStroke(hasError: Boolean) {
        val strokeWidth = resources.getDimensionPixelSize(R.dimen.text_input_stroke_width)

        if (hasError) {
            error = "error"
            boxStrokeWidth = strokeWidth
            boxStrokeWidthFocused = strokeWidth
        } else {
            error = null
            boxStrokeWidth = 0
            boxStrokeWidthFocused = 0
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}