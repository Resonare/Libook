package com.example.test3.data.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.test3.data.db.LibookDatabase
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Thought
import com.example.test3.data.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {
    val booksList: LiveData<List<Book>>
    private val repository: Repository

    var title by mutableStateOf("")
    var author by mutableStateOf("")
    var description by mutableStateOf("")
    var worldRate by mutableStateOf<Float?>(null)
    var coverUri by mutableStateOf<String?>(null)
    var isFavourite by mutableStateOf(false)
    var thoughtContent by mutableStateOf("")
    var thoughtColor by mutableStateOf< Color?>(null)

    init {
        val db = LibookDatabase.getInstance(application)
        val bookDao = db.bookDao()
        val thoughtDao = db.thoughtDao()
        repository = Repository(bookDao, thoughtDao)
        booksList = repository.booksList
    }

    fun getBook(id: String): LiveData<Pair<Book, List<Thought>>?> {
        return repository.getOne(id)
    }

    fun addBook(onFinish: (String) -> Unit) {
        val book = Book(
            title = title,
            author = author,
            description = description,
            worldRate = worldRate,
            coverUri = coverUri,
            isFavourite = isFavourite,
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
            onFinish(book.id)
        }
    }

    fun editBook(id: String, onFinish: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editBook(
                Book(
                    id = id,
                    title = title,
                    author = author,
                    description = description,
                    worldRate = worldRate,
                    coverUri = coverUri,
                    isFavourite = isFavourite,
                )
            )
            onFinish()
        }
    }

    fun deleteBook(id: String, onFinish: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(id)
            onFinish()
        }
    }

    fun addThought(bookId: String) {
        val thought = Thought(
            content = thoughtContent,
            color = thoughtColor ?: Color.Transparent,
            bookId = bookId,
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addThought(thought)
        }
    }

    fun deleteThought(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteThought(id)
        }
    }
}