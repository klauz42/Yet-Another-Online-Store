package ru.klauz42.yetanotheronlinestore.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ru.klauz42.yetanotheronlinestore.R
import ru.klauz42.yetanotheronlinestore.appComponent
import ru.klauz42.yetanotheronlinestore.databinding.ActivityMainBinding
import ru.klauz42.yetanotheronlinestore.di.components.ActivityComponent
import ru.klauz42.yetanotheronlinestore.di.components.DaggerActivityComponent
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.presentation.signin.SignInActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var activityComponent: ActivityComponent
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    @Inject
    lateinit var userDataRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent(appComponent)
            .activity(this).build()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()
    }

    override fun onStart() {
        super.onStart()

        //this is oversimplified implementation of auth checking
        runBlocking {
            if (userDataRepository.getUserData().first().firstName.isEmpty()) {
                signIn()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNavigation() {
        bottomNav = binding.bottomNavigation.apply {
            selectedItemId = R.id.destination_main

            setupWithNavController(navController)
        }
    }

    private fun signIn() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
        finish()
        return
    }
}