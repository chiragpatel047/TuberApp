package com.advanced.base.models

data class Format(
    val approxDurationMs: String,
    val audioChannels: Int,
    val audioQuality: String,
    val audioSampleRate: String,
    val averageBitrate: Int,
    val bitrate: Int,
    val contentLength: String,
    val fps: Int,
    val height: Int,
    val itag: Int,
    val lastModified: String,
    val mimeType: String,
    val projectionType: String,
    val quality: String,
    val qualityLabel: String,
    val url: String,
    val width: Int
)