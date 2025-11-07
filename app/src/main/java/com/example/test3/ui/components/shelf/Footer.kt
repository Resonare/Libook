package com.example.test3.ui.components.shelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.test3.R

@Composable
fun Footer(
    modifier: Modifier,
    onAddBookManually: () -> Unit,
    onAddBookScan: () -> Unit,
) {
    var addMethodSelectionIsVisible by remember { mutableStateOf(false) }

    Box (
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.68f)
                .dropShadow(
                    shape = RectangleShape,
                    shadow = Shadow(
                        radius = 10.dp,
                        spread = 0.dp,
                        color = Color(0x40000000),
                        offset = DpOffset(x = 0.dp, y = (-3).dp)
                    )
                )
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_book),
                    contentDescription = "Shelf menu",
                    contentScale = ContentScale.Fit,
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_book),
                    contentDescription = "Shelf menu",
                    contentScale = ContentScale.Fit,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter)
                .clickable {
                    addMethodSelectionIsVisible = true
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.ic_plus),
                contentDescription = "Add",
                contentScale = ContentScale.Fit
            )
        }

        if (addMethodSelectionIsVisible) {
            AddBookMethodSelection(
                onClose = {
                    addMethodSelectionIsVisible = false
                },
                onAddBookManually = onAddBookManually,
                onAddBookScan = onAddBookScan,
            )
        }
    }
}