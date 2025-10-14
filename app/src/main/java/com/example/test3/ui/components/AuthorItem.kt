package com.example.test3.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test3.Book
import com.example.test3.ui.theme.Black80
import com.example.test3.ui.theme.Green100
import com.example.test3.ui.theme.Red100

@Composable
fun AuthorItem(book: Book) {
    val worldRateColor = when(book.worldRate) {
        in 1..4 -> Red100
        in 7..10 -> Green100
        else -> Black80
    }

    Row {
        if (book.worldRate != null) {
            Text(
                modifier = Modifier,
                text = book.worldRate.toString(),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black),
                color = worldRateColor,
            )

            Spacer(Modifier.width(5.dp))
        }

        Text(
            modifier = Modifier,
            text = book.author,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}