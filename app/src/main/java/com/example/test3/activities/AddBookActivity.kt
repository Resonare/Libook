package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.upsert.Form
import com.example.test3.ui.components.book.upsert.saveExternalImageToInternalStorage
import com.example.test3.ui.theme.LibookTheme
import kotlinx.coroutines.launch

class AddBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val code = intent.getStringExtra("code")

        val tryToAddBook = { viewModel: BookViewModel ->
            if (
                !viewModel.title.isEmpty()
                && !viewModel.author.isEmpty()
                && viewModel.coverUri != null
            ) {
                viewModel.addBook { bookId ->
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

        val showIsSavingCoverHint = {
            Toast.makeText(
                this,
                R.string.saving_cover_hint,
                Toast.LENGTH_LONG
            ).show()
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            if (code != null) {
                viewModel.searchBookByIsbn(code) {
                    if (viewModel.coverUri != null && !viewModel.coverUri!!.isBlank()) {
                        scope.launch {
                            viewModel.isSavingCover = true

                            val loadedUri = saveExternalImageToInternalStorage(context, viewModel.coverUri!!)

                            viewModel.isSavingCover = false

                            if (loadedUri != null)
                                viewModel.coverUri = loadedUri
                        }
                    }
                }
            }

            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                    ) {
                        if (viewModel.isLoadingBook) {
                            Column (
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.scanner_loading_hint),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(Modifier.height(10.dp))

                                CircularProgressIndicator()
                            }
                        } else {
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
                                        if (viewModel.isSavingCover) {
                                            showIsSavingCoverHint()
                                        } else {
                                            tryToAddBook(viewModel)
                                        }
                                    },
                                    isDisabled = viewModel.isSavingCover,
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
}



