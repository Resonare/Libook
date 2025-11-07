package com.example.test3.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    painter: Painter,
    isActive: Boolean = false,
    onClick: () -> Unit,
    contentDescription: String = "Button",
) {
    val bgColor by animateColorAsState(
        targetValue =
            if (isActive)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surface
    )

    val icColor by animateColorAsState(
        targetValue =
            if (isActive)
                MaterialTheme.colorScheme.background
            else
                MaterialTheme.colorScheme.secondary
    )

    Box (
        modifier = Modifier
            .width(50.dp)
            .aspectRatio(1f)
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 3.dp,
                    spread = 0.dp,
                    color = Color(0x40000000),
                    offset = DpOffset(x = 0.dp, y = 3.dp)
                )
            )
            .clip(CircleShape)
            .background(bgColor)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(icColor)
        )
    }
}