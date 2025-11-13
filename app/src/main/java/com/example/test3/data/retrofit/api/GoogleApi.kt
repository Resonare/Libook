package com.example.test3.data.retrofit.api

import com.example.test3.data.retrofit.models.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleApi {
    @GET("volumes")
    suspend fun getBookByIsbn(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): GoogleBooksResponse
}