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
                hintText = stringResource(R.string.search_hint),
                showTwoInRow = showTwoInRow,
                onShowInRowChange = {
                    showTwoInRow = !showTwoInRow
                }
            )

            Spacer(Modifier.height(15.dp))

            FilterBar(handleFilterSelect)

            Spacer(Modifier.height(15.dp))

            BooksGrid(viewModel, showTwoInRow, handleShowBook)
        }
    }
}