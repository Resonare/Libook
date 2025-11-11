package com.example.test3.ui.components.shelf

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.test3.data.entities.Book
import com.example.test3.ui.components.book.show.BookCard

@Composable
fun BooksGrid(
    booksList: List<Book>,
    showTwoInRow: Boolean,
    onBookClick: (Book) -> Unit
) {
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