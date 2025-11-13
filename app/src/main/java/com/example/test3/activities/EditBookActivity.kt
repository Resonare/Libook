package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.upsert.Form
import com.example.test3.ui.theme.LibookTheme

class EditBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bookId = intent.getStringExtra("bookId")

        if (bookId == null) {
            Toast.makeText(this, R.string.error_book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val tryToEditBook = { viewModel: BookViewModel, bookId: String ->
            if (
                !viewModel.title.isEmpty()
                && !viewModel.author.isEmpty()
                && viewModel.coverUri != null
            ) {
                viewModel.editBook(bookId) {
                    val intent = Intent(this, ShowBookActivity::class.java)
                    intent.putExtra("bookId", bookId)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(
                    this,
                    R.string.error_required_fields_are_empty,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            val bookFullTriple by viewModel.getBook(bookId).observeAsState()
            val book = if (bookFullTriple != null) bookFullTriple!!.first else null

            val context = LocalContext.current

            val savedTheme = context
                .getSharedPreferences("settings", MODE_PRIVATE)
                .getBoolean("dark_theme", isSystemInDarkTheme())

            var isDarkTheme by remember { mutableStateOf(savedTheme) }

            LaunchedEffect(book) {
                book?.let {
                    viewModel.title = it.title
                    viewModel.author = it.author
                    viewModel.description = it.description
                    viewModel.worldRate = it.worldRate
                    viewModel.coverUri = it.coverUri
                    viewModel.isFavourite = it.isFavourite
                }
            }

            LibookTheme (
                darkTheme = isDarkTheme
            ) {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CircleButton(
                                painter = painterResource(R.drawable.ic_arrow_left),
                                onClick = {
                                    finish()
                                },
                                contentDescription = "Back button",
                            )

                            CircleButton(
                                painter = painterResource(R.drawable.ic_save),
                                onClick = {
                                    tryToEditBook(viewModel, bookId)
                                },
                                contentDescription = "Save button",
                            )
                        }

                        Box (
                            modifier = Modifier.padding(top = 20.dp)
                        ) {
                            Form(viewModel)
                        }
                    }
                }
            }
        }
    }
}



