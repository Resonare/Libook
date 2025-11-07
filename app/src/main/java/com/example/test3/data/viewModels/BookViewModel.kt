package com.example.test3.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.test3.data.db.LibookDatabase
import com.example.test3.data.entities.Book
import com.example.test3.data.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {
    val booksList: LiveData<List<Book>>
    private val repository: BookRepository

    var title by mutableStateOf("")
    var author by mutableStateOf("")
    var description by mutableStateOf("")
    var worldRate by mutableStateOf<Float?>(null)
    var coverUri by mutableStateOf<String?>(null)

    init {
        val db = LibookDatabase.getInstance(application)
        val bookDao = db.bookDao()
        repository = BookRepository(bookDao)
        booksList = repository.booksList
    }

    fun getBook(id: String): LiveData<Book?> {
        return repository.getOne(id)
    }

    fun addBook(onFinish: (String) -> Unit) {
        val book = Book(
            title = title,
            author = author,
            description = description,
            worldRate = worldRate,
            coverUri = coverUri
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
            onFinish(book.id)
        }
    }

    fun editBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editBook(
                Book(
                    id = id,
                    title = title,
                    author = author,
                    description = description,
                    worldRate = worldRate,
                    coverUri = coverUri
                )
            )
        }
    }

    fun deleteBook(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(id)
        }
    }
}