package com.example.test3.ui.components.book.show

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.ui.components.book.MyThoughtsAdd

@Composable
fun MyThoughtsSection() {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.my_thoughts),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

//        LazyColumn(
//            modifier = Modifier.weight(0.8f)
//        ) {
//            items(book.myThoughts) { myThought ->
//                Spacer(Modifier.height(10.dp))
//                MyThoughtCard(myThought)
//                Spacer(Modifier.height(10.dp))
//            }
//        }

        Spacer(Modifier.height(10.dp))

        MyThoughtsAdd()
    }
}