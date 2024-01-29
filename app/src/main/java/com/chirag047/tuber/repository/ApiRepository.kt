package com.chirag047.tuber.repository

import com.chirag047.tuber.Common.API_HOST
import com.chirag047.tuber.Common.API_KEY
import com.chirag047.tuber.Common.ResponseType
import com.chirag047.tuber.api.YoutubeApi
import com.chirag047.tuber.models.YoutubeModel
import javax.inject.Inject

class ApiRepository @Inject constructor(val youtubeApi: YoutubeApi) {


    var youtubeModel = YoutubeModel()

    suspend fun getResponse(id: String): ResponseType<YoutubeModel> {
        val result = youtubeApi.getResponse(id, API_KEY, API_HOST)

        if (result.isSuccessful && result.body() != null) {
            youtubeModel = result.body()!!
        }

        return try {
            ResponseType.Sucess(youtubeModel)
        } catch (e: Exception) {
            ResponseType.Error(e.message.toString())
        }

    }
}