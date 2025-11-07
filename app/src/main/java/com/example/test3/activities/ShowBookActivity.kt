package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.BookGeneralInfo
import com.example.test3.ui.components.book.show.DeleteBookDialog
import com.example.test3.ui.components.book.show.MyThoughtsSection
import com.example.test3.ui.theme.LibookTheme

class ShowBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bookId = intent.getSerializableExtra("bookId") as? String

        if (bookId == null) {
            Toast.makeText(this, R.string.error_book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val tryToDeleteBook = { viewModel: BookViewModel, book: Book? ->
            if (book != null) {
                viewModel.deleteBook(book.id) {
                    val intent = Intent(this, ShelfActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(
                    this,
                    R.string.error_delete_book,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            val book by viewModel.getBook(bookId).observeAsState()

            var deleteBookDialogIsVisible by remember { mutableStateOf(false) }

            val blurRadius by animateFloatAsState(
                targetValue = if (deleteBookDialogIsVisible) 16f else 0f,
                animationSpec = tween(durationMillis = 500)
            )

            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    if (book != null) {
                        Box (
                            modifier =
                                if (deleteBookDialogIsVisible)
                                    Modifier
                                        .fillMaxSize()
                                        .blur(radius = blurRadius.dp)
                                        .padding(innerPadding)
                                        .padding(horizontal = 20.dp)
                                else
                                    Modifier
                                        .fillMaxSize()
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

                                Column {
                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_share),
                                        onClick = {

                                        },
                                        contentDescription = "Share button",
                                    )

                                    Spacer(Modifier.height(20.dp))

                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_favourite),
                                        onClick = {

                                        },
                                        contentDescription = "Favourite button",
                                    )

                                    Spacer(Modifier.height(20.dp))

                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_edit_dark),
                                        onClick = {

                                        },
                                        contentDescription = "Edit button",
                                    )

                                    Spacer(Modifier.height(20.dp))

                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_delete),
                                        onClick = {
                                            deleteBookDialogIsVisible = true
                                        },
                                        contentDescription = "Delete button",
                                    )
                                }
                            }

                            Column {
                                BookGeneralInfo(book!!)

                                Spacer(Modifier.height(10.dp))

                                MyThoughtsSection()
                            }
                        }

                        AnimatedVisibility(
                            visible = deleteBookDialogIsVisible,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0, 0, 0, 153))
                                    .clickable {
                                        deleteBookDialogIsVisible = false
                                    },
                            )
                        }

                        AnimatedVisibility(
                            visible = deleteBookDialogIsVisible,
                            enter = slideInVertically(
                                initialOffsetY = { it }
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = { it }
                            )
                        ) {
                            Box (
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                DeleteBookDialog (
                                    book = book!!,
                                    onDelete = {
                                        tryToDeleteBook(viewModel, book)
                                    },
                                    onCancel = {
                                        deleteBookDialogIsVisible = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}