package com.example.test3.ui.components.shelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.book.show.BookCard

@Composable
fun BooksGrid(
    viewModel: BookViewModel = viewModel(),
    showTwoInRow: Boolean,
    onBookClick: (Book) -> Unit
) {
    val booksList by viewModel.booksList.observeAsState(listOf())

    LazyVerticalGrid(
        columns = GridCells.Fixed(if (showTwoInRow) 2 else 3),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(booksList) { book ->
            BookCard(book, onBookClick)
        }
    }
}