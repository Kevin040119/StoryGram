package com.example.storyappsubmission.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.response.ListStoryItem

class LoginViewModel : ViewModel(){

    companion object {
        const val TAG = "tag"
    }

    private val _token = MutableLiveData<String>()
    val token : LiveData<String> = _token

}