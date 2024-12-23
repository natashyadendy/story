package com.example.submissiondua.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submissiondua.R
import com.example.submissiondua.databinding.ActivityMainBinding
import com.example.submissiondua.di.DependencyInjector
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.repository.StoryDataRepository
import com.example.submissiondua.ui.add.AddStoryFragment
import com.example.submissiondua.ui.login.LoginActivity
import com.example.submissiondua.ui.maps.StoryMapFragment
import com.example.submissiondua.ui.story.UserStoryFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        FactoryViewModel.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference
    private lateinit var storyRepository: StoryDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(dataStore)
        storyRepository = DependencyInjector.provideStoryRepository(this)

        setupObservers()

        setupBottomNavigation()

        loadFragment(UserStoryFragment())
    }

    private fun setupObservers() {
        mainViewModel.isLoading.observe(this, Observer { isLoading ->
        })

        observeLogoutStatus()
    }

    private fun observeLogoutStatus() {
        mainViewModel.logoutStatus.observe(this, Observer { isLoggedOut ->
            if (isLoggedOut) {
                navigateToLogin()
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_mystory -> {
                    loadFragment(UserStoryFragment())
                    true
                }
                R.id.navigation_uploadstory -> {
                    loadFragment(AddStoryFragment())
                    true
                }
                R.id.navigation_maps -> {
                    loadFragment(StoryMapFragment())
                    true
                }
                R.id.navigation_logout -> {
                    mainViewModel.logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_activity, fragment)
            .commit()
    }

    private fun navigateToLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
