package com.example.test3.ui.components.book.show

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.viewModels.BookViewModel

@Composable
fun MyThoughtsAdd(
    viewModel: BookViewModel = viewModel(),
    innerPadding: PaddingValues,
    bookId: String,
    handleAddThought: (BookViewModel, String) -> Unit,
    isFocused: Boolean,
    handleFocus: (Boolean) -> Unit,
) {
    val isImeOpened = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    val focusManager = LocalFocusManager.current
    if (!isFocused) focusManager.clearFocus()

    Row (
        modifier = Modifier
            .imePadding()
            .padding(if (isFocused && isImeOpened) PaddingValues() else innerPadding)
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
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
            .padding(start = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    focusManager.moveFocus(FocusDirection.Right)
                }
                .padding(horizontal = 5.dp, vertical = 15.dp)
                .weight(0.5f)
                .padding(1.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        handleFocus(focusState.isFocused)
                    },
                value = viewModel.thoughtContent,
                onValueChange = { viewModel.thoughtContent = it },
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 4,
                singleLine = false,
                decorationBox = { innerTextField ->
                    if (viewModel.thoughtContent.isEmpty() && !isFocused) {
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
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    handleAddThought(viewModel, bookId)
                    handleFocus(false)
                }
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.ic_submit),
                contentDescription = "Submit",
                contentScale = ContentScale.Fit,
            )
        }
    }
}