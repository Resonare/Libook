package com.example.test3.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.test3.Book
import com.example.test3.R

@Composable
fun MyThoughtsSection(book: Book) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.my_thoughts),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        LazyColumn {
            items(book.myThoughts) { myThought ->
                Spacer(Modifier.height(5.dp))
                MyThoughtCard(myThought)
                Spacer(Modifier.height(5.dp))
            }
        }
    }
}