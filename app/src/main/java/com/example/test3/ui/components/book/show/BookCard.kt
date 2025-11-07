package com.example.test3.ui.components.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.test3.data.entities.Book


@Composable
fun BookCard(book: Book, onClick: (Book) -> Unit) {
    if (book.coverUri == null) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.66f)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick(book)
            },
    ) {
        AsyncImage(
            model = book.coverUri,
            modifier = Modifier.fillMaxSize(),
            contentDescription = book.title,
            contentScale = ContentScale.FillWidth,
        )
    }
}