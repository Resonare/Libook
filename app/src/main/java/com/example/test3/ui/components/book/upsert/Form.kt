package com.example.test3.ui.components.book.upsert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import kotlin.math.abs
import kotlin.math.round

@Composable
fun Form(viewModel: BookViewModel = viewModel()) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoverPicker(
            value = viewModel.coverUri,
            onLoad = { loadedUri ->
                viewModel.coverUri = loadedUri
            }
        )

        Spacer(Modifier.height(20.dp))

        Column (
            modifier = Modifier.fillMaxWidth(0.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Input(
                value = viewModel.title,
                onValueChange = { newValue ->
                    viewModel.title = newValue
                },
                style = MaterialTheme.typography.titleMedium,
                placeholder = stringResource(R.string.add_book_title_hint)
            )

            Spacer(Modifier.height(10.dp))

            Input(
                value = viewModel.author,
                onValueChange = { newValue ->
                    viewModel.author = newValue
                },
                style = MaterialTheme.typography.bodySmall,
                placeholder = stringResource(R.string.add_book_author_hint)
            )

            Spacer(Modifier.height(25.dp))

            Input(
                value =
                    if (viewModel.worldRate != null)
                        viewModel.worldRate.toString()
                    else
                        "",
                onValueChange = { newValue ->
                    var newWorldRate = newValue.trim().toFloatOrNull()

                    if (newWorldRate != null) {
                        newWorldRate = abs(newWorldRate)
                        newWorldRate = newWorldRate.coerceIn(0f, 10f)
                        newWorldRate = round(newWorldRate * 10) / 10

                        if (newWorldRate < 1f) newWorldRate = null
                    }

                    viewModel.worldRate = newWorldRate

                },
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color =
                    if (viewModel.worldRate != null)
                        Book.getWorldRateColor(viewModel.worldRate!!)
                    else
                        MaterialTheme.colorScheme.secondary,
                placeholder = "-",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.add_book_world_rate_hint),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(Modifier.height(25.dp))

        DescriptionForm(
            value = viewModel.description,
            onValueChange = { newValue ->
                viewModel.description = newValue
            }
        )
    }
}