package com.example.iptvplayer2.data.model

data class EpgProgram(
    val channelId: String,
    val title: String,
    val description: String?,
    val start: String, // Keep as string for now, parse when needed
    val stop: String
)
