package com.example.test3.ui.components.scanner

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.test3.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerWithPermission(onCodeDetected: (String) -> Unit) {
    val permissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    when {
        permissionState.status.isGranted -> {
            Scanner(
                onCodeDetected = onCodeDetected
            )
        } else -> {
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
            }
            Text(
                text = stringResource(R.string.scanner_permission_hint),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}