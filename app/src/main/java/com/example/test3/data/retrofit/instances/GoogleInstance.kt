package com.example.test3.data.retrofit.instances

import com.example.test3.data.retrofit.api.GoogleApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val api: GoogleApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleApi::class.java)
    }
}