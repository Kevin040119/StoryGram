package com.example.storyappsubmission.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappsubmission.R
import com.example.storyappsubmission.data.TokenPreferences
import com.example.storyappsubmission.data.dataStore
import com.example.storyappsubmission.databinding.ActivityStoryBinding
import com.example.storyappsubmission.response.ListStoryItem

class StoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStoryBinding
    private lateinit var tokenViewModel : TokenViewModel
    private var token : String? = null

    companion object {
        const val TOKEN_KEY = "token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DataStore
        val tokenPreferences = TokenPreferences.getInstance(applicationContext.dataStore)
        val storyViewModel = ViewModelProvider(this, ViewModelFactory(tokenPreferences)).get(StoryViewModel::class.java)

        //TokenViewModel
        val pref = TokenPreferences.getInstance(application.dataStore)
        tokenViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvStory.addItemDecoration(itemDecoration)

        storyViewModel.story.observe(this) {item ->
            setStory(item)
        }

        storyViewModel.tokens.observe(this) {tokens ->
            token = tokens
        }

        binding.plusButton.setOnClickListener{
            val intent = Intent(this@StoryActivity, CameraActivity::class.java)
            intent.putExtra(TOKEN_KEY, token)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                tokenViewModel.saveToken("")

                val intent = Intent(this@StoryActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setStory(item : List<ListStoryItem>) {
        val adapter = StoryAdapter()
        adapter.submitList(item)
        binding.rvStory.adapter = adapter

    }

    private fun showLoading(isLoading : Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

}