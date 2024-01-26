package com.advanced.base.models

data class StreamingData(
    val adaptiveFormats : List<AdaptiveFormat> = emptyList(),
    val formats: List<Format> = emptyList()
)