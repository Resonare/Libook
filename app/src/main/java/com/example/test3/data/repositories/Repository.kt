package com.example.test3.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.test3.data.dao.BookDao
import com.example.test3.data.dao.ThoughtDao
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Thought

class Repository(
    private val bookDao: BookDao,
    private val thoughtDao: ThoughtDao,
) {
    val booksList: LiveData<List<Book>> = bookDao.getAll()

    fun getOne(id: String): LiveData<Pair<Book, List<Thought>>?>{
        return bookDao.getOne(id).map { bookWithThoughts ->
            bookWithThoughts?.let { it.book to it.thoughts }
        }
    }

    suspend fun addBook(book: Book): String {
        bookDao.insert(book)
        return book.id
    }

    suspend fun editBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(id: String) = bookDao.delete(id)

    suspend fun addThought(thought: Thought) = thoughtDao.insert(thought)

    suspend fun deleteThought(id: String) = thoughtDao.delete(id)
}