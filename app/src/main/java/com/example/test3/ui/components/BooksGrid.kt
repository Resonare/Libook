package com.example.test3.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.test3.Book
import com.example.test3.R

@Composable
fun BooksGrid(onBookClick: (Book) -> Unit) {
    val booksList = listOf(
        Book("Harry Potter and the Goblet of Fire", R.drawable.cover1),
        Book("Harry Potter and the Chamber of Secrets", R.drawable.cover2),
        Book("Harry Potter and the Half-Blood Prince", R.drawable.cover3),
        Book("Harry Potter and the Deathly Hallows", R.drawable.cover4),
        Book("Harry Potter and the Sorcerer's Stone", R.drawable.cover5),
        Book("Harry Potter and the Deathly Hallows", R.drawable.cover6),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(booksList) { book ->
            BookCard(book, onBookClick)
        }
    }
}