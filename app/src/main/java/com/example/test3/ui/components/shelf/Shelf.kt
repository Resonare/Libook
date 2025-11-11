package com.example.test3.ui.components.shelf

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.other.FilterOption

@Composable
fun Shelf(
    innerPadding: PaddingValues,
    viewModel: BookViewModel = viewModel(),
    handleShowBook: (Book) -> Unit,
    handleFilterSelect: (FilterOption) -> Unit
) {
    var showTwoInRow by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    val booksList by viewModel.booksList.observeAsState(listOf())

    val filteredBooksList = booksList.filter { book ->
        val query = searchQuery.trim().lowercase()

        if (query.isBlank()) return@filter true

        book.title.lowercase().contains(query) || book.author.lowercase().contains(query)
    }

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .padding(bottom = 70.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxHeight(),
        ) {
            SearchBar(
                searchQuery = searchQuery,
                handleChange = {
                    searchQuery = it
                },
                hintText = stringResource(R.string.search_hint),
                showTwoInRow = showTwoInRow,
                onShowInRowChange = {
                    showTwoInRow = !showTwoInRow
                }
            )

            Spacer(Modifier.height(15.dp))

            FilterBar(handleFilterSelect)

            Spacer(Modifier.height(15.dp))

            BooksGrid(filteredBooksList, showTwoInRow, handleShowBook)
        }
    }
}