package com.example.storyappsubmission.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.util.Executors
import com.example.storyappsubmission.data.TokenPreferences
import com.example.storyappsubmission.data.dataStore
import com.example.storyappsubmission.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = TokenPreferences.getInstance(application.dataStore)

        binding.login.setOnClickListener{
            lifecycleScope.launch {
                val token = pref.getToken().first()
                Log.d("TOKEN", token)

                if(token.isNullOrBlank()){
                    val intent = Intent(this@MainActivity, LoginPage::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@MainActivity, StoryActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.register.setOnClickListener{
            val intent = Intent(this@MainActivity, RegisterPage::class.java)
            startActivity(intent)
        }

        setupView()
    }

    //Setup Fullscreen
    private fun setupView() {
        @Suppress("DEPRECATION")
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
}