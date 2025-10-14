package com.example.test3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
fun SearchBar(modifier: Modifier = Modifier, hintText: String = "") {
    /*
        TODO(
            1. Fix "two in row button"
            2. Fix query text overflow scrolling
         )
    */

    var queryText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .padding(0.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused && !isFocused
            },
        value = queryText,
        onValueChange = { queryText = it },
        textStyle = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.secondary),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row (
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
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
                        .padding(0.dp),
                ) {
                    Image(
                        painterResource(R.drawable.ic_search),
                        contentDescription = "Search icon",
                        contentScale = ContentScale.Fit,
                    )

                    Spacer(Modifier.width(8.dp))

                    if (queryText.isEmpty() && !isFocused) {
                        Text(
                            text = hintText,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.displayMedium
                        )
                    } else {
                        innerTextField()
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(30.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(R.drawable.ic_two_books_in_row),
                        contentDescription = "Two books in row mode",
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    )
}