package com.example.test3.ui.components.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test3.R

@Composable
fun MyThoughtsAdd() {
    var myThoughtValue by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .dropShadow(
                    shape = RoundedCornerShape(5.dp),
                    shadow = Shadow(
                        radius = 6.dp,
                        spread = 1.dp,
                        color = Color(0x40000000),
                    )
                )
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 5.dp)

            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 15.dp)
                    .weight(0.5f)
                    .padding(1.dp),
            ) {
                BasicTextField(
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused && !isFocused
                        },
                    value = myThoughtValue,
                    onValueChange = { myThoughtValue = it },
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Normal
                    ),
                    decorationBox = { innerTextField ->
                        if (myThoughtValue.isEmpty() && !isFocused) {
                            Text(
                                text = stringResource(R.string.my_thought_add_hint),
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
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
}