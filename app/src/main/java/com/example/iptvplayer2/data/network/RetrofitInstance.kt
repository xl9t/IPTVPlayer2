package com.example.iptvplayer2.data.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {
    val api: M3uApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost/") // Base URL is required but will be overridden by @Url
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(M3uApi::class.java)
    }
}
