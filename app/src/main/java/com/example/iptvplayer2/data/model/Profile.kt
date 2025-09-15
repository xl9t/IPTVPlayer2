package com.example.iptvplayer2.data.model

import androidx.annotation.DrawableRes

data class Profile(
    val id: String,
    val name: String,
    @DrawableRes val avatar: Int? = null, // Using drawable resource for avatar
    val isLocked: Boolean = false
)
