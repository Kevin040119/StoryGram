package com.example.storyappsubmission.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.storyappsubmission.R
import com.example.storyappsubmission.databinding.ActivityStoryDetailBinding
import java.lang.System.load

class StoryDetail : AppCompatActivity() {

    private lateinit var binding : ActivityStoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var photo = intent.getStringExtra("photo")

        setupData(name, description, photo)

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

    private fun setupData(name : String?, description : String?, photo : String?) {
        binding.storyTitle.text = name
        binding.storyDescription.text = description

        Glide.with(this)
            .load(photo)
            .into(binding.storyImg)
    }
}