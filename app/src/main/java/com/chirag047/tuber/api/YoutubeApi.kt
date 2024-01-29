package com.chirag047.tuber.api

import com.chirag047.tuber.models.YoutubeModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YoutubeApi {
    @GET("/video")
    suspend fun getResponse(
        @Query("id") id: String,
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String
    ): Response<YoutubeModel>
}