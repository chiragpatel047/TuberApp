package com.chirag047.tuber.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chirag047.tuber.Common.ResponseType
import com.chirag047.tuber.models.YoutubeModel
import com.chirag047.tuber.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val apiRepository: ApiRepository) : ViewModel() {

    private val _data = MutableStateFlow<ResponseType<YoutubeModel>>(value = ResponseType.Loading())
    val data: StateFlow<ResponseType<YoutubeModel>>
        get() = _data

    fun getResponse(id: String) {
        viewModelScope.launch {
            val result = apiRepository.getResponse(id)
            _data.emit(result)
        }
    }
}
