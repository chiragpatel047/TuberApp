package com.chirag047.tuber.models

data class VideoDetails(
    val channelId: String = "",
    val title: String = "",
    val videoId: String = "",
    var thumbnail: Thumbnail = Thumbnail(emptyList())
)