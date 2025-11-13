package com.example.test3.ui.components.book.upsert

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.test3.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun CoverPicker(value: String?, onLoad: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                val loadedUri = saveInternalImageToInternalStorage(context, it)

                if (loadedUri != null)
                    onLoad(loadedUri)
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.47f)
            .aspectRatio(0.66f)
            .dropShadow(
                shape = RoundedCornerShape(12.dp),
                shadow = Shadow(
                    radius = 6.dp,
                    spread = 1.dp,
                    color = Color(0x40000000),
                )
            )
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                launcher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        if (value == null) {
            Box (
                modifier = Modifier
                    .fillMaxWidth(0.2f),
            ) {
                Image(
                    painterResource(R.drawable.ic_plus_dark),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Add cover",
                    contentScale = ContentScale.FillWidth,
                )
            }
        } else {
            CircularProgressIndicator()

            AsyncImage(
                model = value,
                contentDescription = "Cover",
                error = painterResource(R.drawable.ic_error_dark),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

suspend fun saveInternalImageToInternalStorage(
    context: Context,
    uri: Uri
): String? = withContext(Dispatchers.IO) {
    try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val fileName = "cover_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)

            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }

            inputStream.close()

            Uri.fromFile(file).toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun saveExternalImageToInternalStorage(
    context: Context,
    url: String
): String? = withContext(Dispatchers.IO) {
    try {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as? SuccessResult) ?: return@withContext null
        val bitmap = result.drawable.toBitmap()

        val fileName = "cover_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }

        Uri.fromFile(file).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}