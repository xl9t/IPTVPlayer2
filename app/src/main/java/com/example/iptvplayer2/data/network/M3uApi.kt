package com.example.iptvplayer2.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface M3uApi {
    @GET
    suspend fun getM3u(@Url url: String): Response<String>
}
