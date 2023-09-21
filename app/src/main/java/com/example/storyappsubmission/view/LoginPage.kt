package com.example.storyappsubmission.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.storyappsubmission.R
import com.example.storyappsubmission.data.TokenPreferences
import com.example.storyappsubmission.data.dataStore
import com.example.storyappsubmission.databinding.ActivityLoginPageBinding
import com.example.storyappsubmission.response.LoginResponse
import com.example.storyappsubmission.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var binding : ActivityLoginPageBinding
    private lateinit var tokenViewModel : TokenViewModel

    companion object {
        const val TAG = "tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = TokenPreferences.getInstance(application.dataStore)
        tokenViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            TokenViewModel::class.java
        )

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener{view ->

            var mail = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()
            login(mail, password)

            //Sembuyiin Keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun login(mail : String, password : String) {
        //loading
        val client = ApiConfig.getApiService().login(mail, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                //loading
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    Log.d(RegisterPage.TAG, "succeed")
                    val token = responseBody.loginResult?.token

                    //Save to DataStore
                    token?.let {
                        tokenViewModel.saveToken(it)
                    }

                    showAlert(mail)
                } else {
                    Log.d(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(RegisterPage.TAG, "onFailures: ${t.message}")
            }

        })
    }

    private fun showAlert(email : String) {
        //Alert
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Login Succeed")
            setPositiveButton("Finish") { _, _ ->
                val intent = Intent(this@LoginPage, StoryActivity::class.java)
                startActivity(intent)
            }
            create()
            show()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logoMenu2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.loginTitle, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val pass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, email, emailLayout, pass, passLayout, login)
            start()
        }
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