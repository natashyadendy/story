package com.example.submissiondua.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.example.submissiondua.databinding.WelcomePageBinding
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.submissiondua.pref.UserPreference
import com.example.submissiondua.ui.MainActivity
import com.example.submissiondua.ui.dataStore
import com.example.submissiondua.ui.login.LoginActivity
import com.example.submissiondua.ui.signup.SignupActivity
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: WelcomePageBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference.getInstance(dataStore)

        initUI()
        observeUserLoginStatus()
    }

    private fun initUI() {
        hideStatusBar()
        setupActionButtons()
        playAnimations()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun observeUserLoginStatus() {
        lifecycleScope.launch {
            userPreference.getUserInfo().collect { user ->
                user.token.takeIf { it.isNotEmpty() }?.let {
                    navigateToMainActivity()
                }
            }
        }
    }

    private fun setupActionButtons() {
        binding.loginButton.setOnClickListener {
            navigateToLoginActivity()
        }

        binding.signupButton.setOnClickListener {
            navigateToSignupActivity()
        }
    }

    private fun navigateToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun navigateToSignupActivity() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    private fun playAnimations() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        AnimatorSet().apply {
            playSequentially(
                createAlphaAnimation(binding.titleTextView),
                createAlphaAnimation(binding.descTextView),
                createTogetherAnimation()
            )
            start()
        }
    }

    private fun createAlphaAnimation(view: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1f).setDuration(100)
    }

    private fun createTogetherAnimation(): AnimatorSet {
        return AnimatorSet().apply {
            playTogether(
                createAlphaAnimation(binding.loginButton),
                createAlphaAnimation(binding.signupButton)
            )
        }
    }
}
