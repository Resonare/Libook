package com.example.test3.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.test3.data.dao.BookDao
import com.example.test3.data.dao.RateDao
import com.example.test3.data.dao.ThoughtDao
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.Thought

class Repository(
    private val bookDao: BookDao,
    private val thoughtDao: ThoughtDao,
    private val rateDao: RateDao,
) {
    val booksList: LiveData<List<Book>> = bookDao.getAll()

    fun getOneBook(id: String): LiveData<Triple<Book, List<Thought>, List<Rate>>?>{
        return bookDao.getOne(id).map { bookFull ->
            bookFull?.let { Triple(it.book, it.thoughts, it.rates) }
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

    suspend fun addRate(rate: Rate) = rateDao.insert(rate)

    suspend fun editRate(rate: Rate) = rateDao.update(rate)

    suspend fun deleteRate(id: String) = rateDao.delete(id)
}