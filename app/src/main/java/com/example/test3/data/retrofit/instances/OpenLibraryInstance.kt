package com.example.test3.data.retrofit.instances

import com.example.test3.data.retrofit.api.OpenLibraryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenLibraryInstance {
    private const val BASE_URL = "https://openlibrary.org/api/"

    val api: OpenLibraryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApi::class.java)
    }
}