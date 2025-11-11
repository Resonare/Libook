package com.example.test3.ui.components.rate

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test3.ui.theme.Black80
import com.example.test3.ui.theme.Green100
import com.example.test3.ui.theme.Red100

@Composable
fun RateCarousel(listState: LazyListState, currentIndex: Int) {

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val density = LocalDensity.current
    val elementWidth = 100.dp
    val screenWidth = with(density) {
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    val sidePadding = (screenWidth - elementWidth) / 2

    val circleColor by animateColorAsState(
        targetValue = when(currentIndex) {
            in 1..4 -> Red100
            in 7..10 -> Green100
            else -> Black80
        },
        animationSpec = tween(durationMillis = 300)
    )

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        circleColor,
                        size.minDimension / 2,
                        size.center,
                        blendMode = BlendMode.SrcOut
                    )
                }
        ) {
            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(horizontal = sidePadding)
            ) {
                items(11) { index ->
                    Box(
                        modifier = Modifier
                            .size(elementWidth)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (index == 0) "-" else index.toString(),
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 56.sp)
                        )
                    }
                }
            }
        }
    }
}