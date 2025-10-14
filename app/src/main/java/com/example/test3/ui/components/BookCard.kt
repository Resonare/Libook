package com.example.test3.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.test3.Book


@Composable
fun BookCard(book: Book, onClick: (Book) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.66f)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick(book)
            },
    ) {
        Image(
            painterResource(book.coverId),
            modifier = Modifier.fillMaxSize(),
            contentDescription = book.title,
            contentScale = ContentScale.FillWidth,
        )
    }
}