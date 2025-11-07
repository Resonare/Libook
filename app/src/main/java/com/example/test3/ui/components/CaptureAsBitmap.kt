package com.example.test3.ui.components

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

@Composable
fun CaptureAsBitmap(content: @Composable () -> Unit, allIsLoaded: Boolean, onBitmapReady: (Bitmap) -> Unit) {
    AndroidView(
        modifier = Modifier.alpha(0f),
        factory = { ctx ->
            ComposeView(ctx).apply {
                setContent { content() }
            }
        },
        update = { view ->
            if (allIsLoaded) {
                view.post {
                    val bitmap = view.drawToBitmap()
                    onBitmapReady(bitmap)
                }
            }
        }
    )
}