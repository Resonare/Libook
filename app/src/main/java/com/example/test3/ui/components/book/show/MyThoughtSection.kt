package com.example.test3.ui.components.book.show

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
import com.example.test3.R
import com.example.test3.data.entities.Thought

@Composable
fun MyThoughtsSection(
    thoughts: List<Thought>,
    handleDeleteThought: (String) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.my_thoughts),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        LazyColumn(
            modifier = Modifier.weight(0.8f)
        ) {
            items(
                items = thoughts,
                key = { it.id }
            ) { thought ->
                Spacer(Modifier.height(10.dp))
                MyThoughtCard(thought, handleDeleteThought)
                Spacer(Modifier.height(10.dp))
            }
        }

        Spacer(Modifier.height(70.dp))
    }
}