package com.example.test3.ui.components.book.show

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Rate
import com.example.test3.ui.components.book.AuthorItem

@Composable
fun BookGeneralInfo(
    book: Book,
    rates: List<Rate> = emptyList(),
    shareVariant: Boolean = false,
    onCoverClick: () -> Unit = {},
    onCoverLoaded: () -> Unit = {},
    onRateClick: (String?) -> Unit = {}
) {
    Column (
        modifier =
            if (shareVariant)
                Modifier.fillMaxSize()
            else
                Modifier.fillMaxWidth().fillMaxHeight(0.66f).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().padding(top = if (shareVariant) 70.dp else 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (shareVariant) 0.65f else 0.47f)
                    .aspectRatio(0.66f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        onCoverClick()
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (book.coverUri != null) {
                    CircularProgressIndicator()

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.coverUri)
                            .allowHardware(false)
                            .build(),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = book.title,
                        contentScale = ContentScale.Crop,
                        onState = { state ->
                            if (state is AsyncImagePainter.State.Success) {
                                onCoverLoaded()
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(5.dp))

                AuthorItem(book)
            }
        }

        Spacer(Modifier.height(10.dp))

        FlowRow (
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(
                space = 17.dp,
                alignment = Alignment.CenterHorizontally,
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (rates.isEmpty()) {
                RateItem(onRateClick = onRateClick)
            } else {
                rates.forEach {
                    RateItem(rate = it, onRateClick = onRateClick)
                }
            }
        }

        if (!shareVariant) {
            Spacer(Modifier.height(10.dp))

            BookDescriptionItem(book)
        }
    }
}