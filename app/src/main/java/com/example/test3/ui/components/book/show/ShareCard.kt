package com.example.test3.ui.components.book.show

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Rate
import com.example.test3.ui.components.CaptureAsBitmap
import java.io.File
import java.io.FileOutputStream

@Composable
fun ShareCard(
    book: Book,
    rates: List<Rate>,
    isSharing: Boolean,
    onResult: (ActivityResult) -> Unit,
    innerPadding: PaddingValues,
) {
    val context = LocalContext.current
    val shareTitle = stringResource(R.string.share_title)

    var isCoverLoaded by remember { mutableStateOf(false) }

    if (isSharing) {
        val shareLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = onResult
        )

        CaptureAsBitmap(
            content = {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(innerPadding)
                        .padding(top = 40.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    BookGeneralInfo(
                        book = book,
                        rates = rates,
                        descriptionAlwaysFull = true,
                        onCoverLoaded = {
                            isCoverLoaded = true
                        }
                    )
                }
            },
            allIsLoaded = isCoverLoaded
        ) { bitmap ->
            val bitmapUri = saveBitmapToCache(context, bitmap)

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, bitmapUri)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            shareLauncher.launch(Intent.createChooser(shareIntent, shareTitle))
        }
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap, fileName: String = "shared_image.png"): Uri {
    val file = File(context.cacheDir, fileName)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.file_provider",
        file
    )
}