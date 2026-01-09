package com.example.test3.ui.components.book.show

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.test3.R
import com.example.test3.data.entities.Thought

@Composable
fun MyThoughtsSection(
    thoughts: List<Thought>,
    handleDeleteThought: (String) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.my_thoughts),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            items(
                items = thoughts,
                key = { it.id }
            ) { thought ->
                MyThoughtCard(thought, handleDeleteThought)
            }
        }
    }
}