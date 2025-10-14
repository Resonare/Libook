package com.example.test3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test3.ui.components.BookGeneralInfo
import com.example.test3.ui.components.MyThoughtsSection
import com.example.test3.ui.theme.LibookTheme

class ShowBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val book = intent.getSerializableExtra("book") as? Book

        if (book == null) {
            Toast.makeText(this, R.string.error_book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContent {
            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                    ) {
                        Column {
                            BookGeneralInfo(book)

                            Spacer(Modifier.height(10.dp))

                            MyThoughtsSection(book)
                        }
                    }
                }
            }
        }
    }
}