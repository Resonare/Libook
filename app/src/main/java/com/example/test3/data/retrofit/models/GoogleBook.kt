package com.example.test3.data.retrofit.models

data class GoogleBooksResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String?,
    val volumeInfo: GoogleBook? // <-- здесь должен быть объект volumeInfo
)

data class GoogleBook(
    val title: String?,
    val authors: List<String>?,
    val description: String? = null,
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)