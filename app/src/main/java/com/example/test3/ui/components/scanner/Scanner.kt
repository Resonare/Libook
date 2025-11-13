package com.example.test3.ui.components.scanner

import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun Scanner(
    onCodeDetected: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    var innerBarcode by remember { mutableStateOf<String?>(null) }
    var boundingRect by remember { mutableStateOf<Rect?>(null) }
    val cameraController = remember {
        LifecycleCameraController(context)
    }

    Box {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    val options = BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                            Barcode.FORMAT_EAN_8,
                            Barcode.FORMAT_EAN_13,
                        )
                        .build()

                    val barcodeScanner = BarcodeScanning.getClient(options)

                    cameraController.setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(ctx), // Use the main executor
                        MlKitAnalyzer(
                            listOf(barcodeScanner),
                            ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
                            ContextCompat.getMainExecutor(ctx) // Use the main executor
                        ) { result: MlKitAnalyzer.Result? ->
                            val barcodeResults = result?.getValue(barcodeScanner)
                            if (!barcodeResults.isNullOrEmpty()) {
                                innerBarcode = barcodeResults.last().rawValue
                                boundingRect = barcodeResults.last().boundingBox
                            }
                        }
                    )

                    cameraController.bindToLifecycle(lifecycleOwner)
                    this.controller = cameraController
                }
            }
        )

        if (innerBarcode != null) {
            onCodeDetected(innerBarcode!!)

            BarcodeRect(rect = boundingRect)
        }
    }
}