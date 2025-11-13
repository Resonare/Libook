package com.example.test3.data.retrofit.models

data class OpenLibraryBook(
    val title: String?,
    val authors: List<Author>?,
    val publish_date: String?,
    val number_of_pages: Int?,
    val cover: Cover?
)

data class Author(
    val name: String?
)

data class Cover(
    val small: String?,
    val medium: String?,
    val large: String?
)