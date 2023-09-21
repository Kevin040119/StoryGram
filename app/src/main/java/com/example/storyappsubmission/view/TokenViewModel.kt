package com.example.storyappsubmission.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.TokenPreferences
import kotlinx.coroutines.launch

class TokenViewModel (private val pref : TokenPreferences) : ViewModel() {

    fun getToken() : LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token : String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

}