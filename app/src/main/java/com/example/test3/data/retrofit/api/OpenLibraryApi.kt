package com.example.test3.data.retrofit.api

import com.example.test3.data.retrofit.models.OpenLibraryBook
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("books?format=json&jscmd=data")
    suspend fun getBookByIsbn(
        @Query("bibkeys") bibkeys: String
    ): Map<String, OpenLibraryBook>
}