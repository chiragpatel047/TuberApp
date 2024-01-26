package com.advanced.base.models

data class YoutubeModel(
    var streamingData: StreamingData = StreamingData(listOf<AdaptiveFormat>()),
    var videoDetails: VideoDetails = VideoDetails("", "", "")
)
