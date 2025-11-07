package com.example.test3.ui.components.book.add

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    style: TextStyle,
    color: Color = MaterialTheme.colorScheme.secondary,
    placeholder: String,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = Modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused && !isFocused
            },
        value = value,
        onValueChange = onValueChange,
        textStyle = style.copy(
            textAlign = TextAlign.Center,
            color = color
        ),
        decorationBox = { innerTextField ->
            Box (
                contentAlignment = Alignment.Center
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = placeholder,
                        style = style,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        }
    )
}