package com.advanced.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.advanced.base.Common.ResponseType
import com.advanced.base.models.YoutubeModel
import com.advanced.base.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val apiRepository: ApiRepository) : ViewModel() {

    private val _data = MutableStateFlow<ResponseType<YoutubeModel>>(value = ResponseType.Nothing())
    val data: StateFlow<ResponseType<YoutubeModel>>
        get() = _data

    fun getResponse(id: String) {
        viewModelScope.launch {
            val result = apiRepository.getResponse(id)
            _data.emit(result)
        }
    }
}
