package com.example.test3.ui.components.book.show

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick(book)
            },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()

        AsyncImage(
            model = book.coverUri,
            modifier = Modifier.fillMaxSize(),
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
        )
    }
}