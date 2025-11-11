package com.example.test3.ui.components.shelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.test3.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    handleChange: (String) -> Unit,
    hintText: String = "",
    showTwoInRow: Boolean,
    onShowInRowChange: () -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    Box {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp)
                .dropShadow(
                    shape = RoundedCornerShape(30.dp),
                    shadow = Shadow(
                        radius = 3.dp,
                        spread = 0.dp,
                        color = Color(0x40000000),
                        offset = DpOffset(x = 0.dp, y = 3.dp)
                    )
                )
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 22.dp)

            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .padding(0.dp)
                    .weight(0.5f),
            ) {
                Image(
                    painterResource(R.drawable.ic_search),
                    contentDescription = "Search icon",
                    contentScale = ContentScale.Fit,
                )

                Spacer(Modifier.width(8.dp))

                BasicTextField(
                    modifier = Modifier
                        .padding(0.dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused && !isFocused
                        },
                    value = searchQuery,
                    onValueChange = { handleChange(it) },
                    textStyle = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.secondary),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty() && !isFocused) {
                            Text(
                                text = hintText,
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                        innerTextField()
                    }
                )

            }

            Spacer(Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .clickable {
                        onShowInRowChange()
                    }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(if (showTwoInRow) R.drawable.ic_three_books_in_row else R.drawable.ic_two_books_in_row),
                    contentDescription = "Two books in row mode",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}