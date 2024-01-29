package com.chirag047.tuber.models

data class StreamingData(
    val adaptiveFormats : List<AdaptiveFormat> = emptyList(),
    val formats: List<Format> = emptyList()
)