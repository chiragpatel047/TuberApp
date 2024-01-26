package com.advanced.base.repository

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.advanced.base.Common.API_HOST
import com.advanced.base.Common.API_KEY
import com.advanced.base.api.YoutubeApi
import com.advanced.base.models.AdaptiveFormat
import com.advanced.base.models.StreamingData
import com.advanced.base.models.VideoDetails
import com.advanced.base.models.YoutubeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ApiRepository @Inject constructor(val youtubeApi: YoutubeApi) {

    private val _data = MutableStateFlow<YoutubeModel>(YoutubeModel())
    val data: StateFlow<YoutubeModel>
        get() = _data

    suspend fun getResponse(id: String) {
        val result = youtubeApi.getResponse(id, API_KEY, API_HOST)

        if (result.isSuccessful && result.body() != null) {
            _data.emit(result.body()!!)
        }
    }
}