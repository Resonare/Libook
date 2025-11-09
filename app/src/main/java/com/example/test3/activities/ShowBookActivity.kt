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
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.ColorPicker
import com.example.test3.ui.components.Overlay
import com.example.test3.ui.components.book.show.BookGeneralInfo
import com.example.test3.ui.components.book.show.DeleteBookDialog
import com.example.test3.ui.components.book.show.MyThoughtsAdd
import com.example.test3.ui.components.book.show.MyThoughtsSection
import com.example.test3.ui.components.book.show.ShareCard
import com.example.test3.ui.theme.LibookTheme

class ShowBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bookId = intent.getStringExtra("bookId")

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

        val handleEditBook = {
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("bookId", bookId)
            startActivity(intent)
        }

        val handleGoToShelf = {
            val intent = Intent(this, ShelfActivity::class.java)
            startActivity(intent)
        }

        val handleFavouriteBook = { viewModel: BookViewModel ->
            if (
                !viewModel.title.isEmpty()
                && !viewModel.author.isEmpty()
                && viewModel.coverUri != null
            ) {
                viewModel.isFavourite = !viewModel.isFavourite
                viewModel.editBook(bookId)
            } else {
                Toast.makeText(
                    this,
                    R.string.error_data_lost,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val handleAddThought = { viewModel: BookViewModel, bookId: String ->
            if (!viewModel.thoughtContent.isEmpty()) {
                viewModel.addThought(bookId)
                viewModel.thoughtContent = ""
            } else {
                Toast.makeText(
                    this,
                    R.string.error_thought_empty,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            val bookWithThoughts by viewModel.getBook(bookId).observeAsState()
            val book = if (bookWithThoughts != null) bookWithThoughts!!.first else null
            val thoughts = if (bookWithThoughts != null) bookWithThoughts!!.second else null

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

            var deleteBookDialogIsVisible by remember { mutableStateOf(false) }
            var myThoughtAddIsFocused by remember { mutableStateOf(false) }

            val blurRadius by animateFloatAsState(
                targetValue = if (deleteBookDialogIsVisible) 16f else 0f,
                animationSpec = tween(durationMillis = 500)
            )

            var isSharing by remember { mutableStateOf(false) }

            val handleDeleteThought = { thoughtId: String ->
                if (!thoughtId.isEmpty()) {
                    viewModel.deleteThought(thoughtId)
                } else {
                    Toast.makeText(
                        this,
                        R.string.error_data_lost,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    if (book != null) {
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius =
                                    if (deleteBookDialogIsVisible)
                                        blurRadius.dp
                                    else
                                        0.dp
                                )
                                .padding(innerPadding)
                                .padding(horizontal = 20.dp)
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                CircleButton(
                                    painter = painterResource(R.drawable.ic_arrow_left),
                                    onClick = handleGoToShelf,
                                    contentDescription = "Back button",
                                )

                                Column {
                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_share),
                                        onClick = {
                                            isSharing = true
                                        },
                                        contentDescription = "Share button",
                                    )

                                    Spacer(Modifier.height(20.dp))

                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_favourite),
                                        onClick = {
                                            handleFavouriteBook(viewModel)
                                        },
                                        isActive = book.isFavourite,
                                        contentDescription = "Favourite button",
                                    )

                                    Spacer(Modifier.height(20.dp))

                                    CircleButton(
                                        painter = painterResource(R.drawable.ic_edit_dark),
                                        onClick = handleEditBook,
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
                                BookGeneralInfo(book)

                                Spacer(Modifier.height(10.dp))

                                if (thoughts != null) {
                                    MyThoughtsSection(thoughts, handleDeleteThought)
                                }
                            }
                        }

                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius =
                                    if (deleteBookDialogIsVisible)
                                        blurRadius.dp
                                    else
                                        0.dp
                                ),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            Overlay(myThoughtAddIsFocused && !deleteBookDialogIsVisible) {
                                myThoughtAddIsFocused = false
                            }

                            Box (
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            ) {
                                MyThoughtsAdd(
                                    viewModel = viewModel,
                                    bookId = bookId,
                                    handleAddThought = handleAddThought,
                                    isFocused = myThoughtAddIsFocused,
                                    handleFocus = { isFocused ->
                                        myThoughtAddIsFocused = isFocused
                                    }
                                )
                            }

                            AnimatedVisibility(
                                modifier = Modifier.align(Alignment.TopCenter),
                                visible = myThoughtAddIsFocused,
                                enter = slideInVertically(
                                    initialOffsetY = { -it }
                                ),
                                exit = slideOutVertically(
                                    targetOffsetY = { -it }
                                )
                            ) {
                                Box (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min)
                                        .padding(innerPadding)
                                        .padding(horizontal = 20.dp)
                                        .dropShadow(
                                            shape = RoundedCornerShape(5.dp),
                                            shadow = Shadow(
                                                radius = 6.dp,
                                                spread = 1.dp,
                                                color = Color(0x40000000),
                                            )
                                        )
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    ColorPicker { color ->
                                        viewModel.thoughtColor = color
                                    }
                                }
                            }
                        }

                        Overlay(deleteBookDialogIsVisible) {
                            deleteBookDialogIsVisible = false
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
                                    book = book,
                                    onDelete = {
                                        tryToDeleteBook(viewModel, book)
                                    },
                                    onCancel = {
                                        deleteBookDialogIsVisible = false
                                    },
                                    innerPadding = innerPadding
                                )
                            }
                        }

                        ShareCard(
                            book = book,
                            isSharing = isSharing,
                            onResult = {
                                isSharing = false
                            },
                            innerPadding = innerPadding,
                        )
                    }
                }
            }
        }
    }
}