package com.advanced.base.models

data class YoutubeModel(
    var streamingData: StreamingData = StreamingData(listOf<AdaptiveFormat>(), listOf<Format>()),
    var videoDetails: VideoDetails = VideoDetails("", "", "")
)
