package com.advanced.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.base.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val apiRepository: ApiRepository) : ViewModel() {

    val data = apiRepository.data

    fun getResponse(id: String) {
        viewModelScope.launch {
            apiRepository.getResponse(id)
        }
    }
}