package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.test3.R
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.scanner.ScannerWithPermission
import com.example.test3.ui.theme.LibookTheme

class ScannerActivity: ComponentActivity() {
    private var isHandled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val handleCodeDetected = { code: String ->
            if (!isHandled) {
                isHandled = true
                val intent = Intent(this, AddBookActivity::class.java)
                intent.putExtra("code", code)
                startActivity(intent)
                finish()
            }
        }

        val handleGoBack = {
            val myIntent = Intent(this, ShelfActivity::class.java)
            startActivity(myIntent)
        }

        setContent {
            val context = LocalContext.current

            val savedTheme = context
                .getSharedPreferences("settings", MODE_PRIVATE)
                .getBoolean("dark_theme", isSystemInDarkTheme())

            var isDarkTheme by remember { mutableStateOf(savedTheme) }

            LibookTheme (
                darkTheme = isDarkTheme
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            ScannerWithPermission(onCodeDetected = handleCodeDetected)
                        }

                        Row (
                            modifier = Modifier.padding(20.dp)
                        ) {
                            CircleButton(
                                painter = painterResource(R.drawable.ic_cancel),
                                onClick = {
                                    handleGoBack()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}