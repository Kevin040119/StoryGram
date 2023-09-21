package com.example.storyappsubmission.view

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.TokenPreferences
import com.example.storyappsubmission.data.dataStore
import com.example.storyappsubmission.response.ListStoryItem
import com.example.storyappsubmission.response.StoryResponse
import com.example.storyappsubmission.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class StoryViewModel(private val tokenPreferences: TokenPreferences) : ViewModel() {

    companion object {
        const val TAG = "tag"
    }

    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story : LiveData<List<ListStoryItem>> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _tokens = MutableLiveData<String>()
    val tokens : LiveData<String> = _tokens

    init {
        showStory()
    }

    private fun showStory() {
        viewModelScope.launch {
            _isLoading.value = true
            //GetToken
            val token = tokenPreferences.getToken().first()
            _tokens.value = token

            val client = ApiConfig.getApiService().getStories("Bearer $token")
            client.enqueue(object : retrofit2.Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _story.value = (response.body()?.listStory as? List<ListStoryItem>?)!!
                    } else {
                        Log.d(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message!!)
                }

            })
        }
    }
}