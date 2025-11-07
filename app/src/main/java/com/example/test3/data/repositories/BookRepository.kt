package com.example.test3.data.repositories

import androidx.lifecycle.LiveData
import com.example.test3.data.dao.BookDao
import com.example.test3.data.entities.Book

class BookRepository(private val dao: BookDao) {
    val booksList: LiveData<List<Book>> = dao.getAll()

    fun getOne(id: String): LiveData<Book?> = dao.getOne(id)

    suspend fun addBook(book: Book): String {
        dao.insert(book)
        return book.id
    }

    suspend fun editBook(book: Book) = dao.update(book)

    suspend fun deleteBook(id: String) = dao.delete(id)
}