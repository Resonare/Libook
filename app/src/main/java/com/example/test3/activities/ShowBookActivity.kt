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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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

        val handleEditRates = { rateTypeName: String? ->
            val intent = Intent(this, EditRatesActivity::class.java)
            intent.putExtra("bookId", bookId)
            intent.putExtra("rateTypeName", rateTypeName)
            startActivity(intent)
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            val bookFullTriple by viewModel.getBook(bookId).observeAsState()
            val book = if (bookFullTriple != null) bookFullTriple!!.first else null
            val thoughts = if (bookFullTriple != null) bookFullTriple!!.second else null
            val rates = if (bookFullTriple != null) bookFullTriple!!.third else null

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
            var isCoverShown by remember { mutableStateOf(false) }

            val mainOverlayIsVisible = deleteBookDialogIsVisible || isCoverShown

            var myThoughtAddIsFocused by remember { mutableStateOf(false) }

            val blurRadius by animateFloatAsState(
                targetValue = if (mainOverlayIsVisible) 16f else 0f,
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

            val context = LocalContext.current

            val savedTheme = context
                .getSharedPreferences("settings", MODE_PRIVATE)
                .getBoolean("dark_theme", isSystemInDarkTheme())

            var isDarkTheme by remember { mutableStateOf(savedTheme) }

            LibookTheme (
                darkTheme = isDarkTheme
            ) {
                Scaffold (
                    modifier = Modifier
                        .fillMaxSize(),
                ) { innerPadding ->
                    if (book != null) {
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius = blurRadius.dp)
                                .padding(innerPadding)
                                .padding(horizontal = 20.dp)
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.92f)
                            ) {
                                BookGeneralInfo(
                                    book = book,
                                    rates = rates ?: emptyList(),
                                    onCoverClick = {
                                        isCoverShown = true
                                    },
                                    onRateClick = { rateTypeName: String? ->
                                        handleEditRates(rateTypeName)
                                    }
                                )

                                if (thoughts != null) {
                                    Spacer(Modifier.height(10.dp))

                                    MyThoughtsSection(thoughts, handleDeleteThought)
                                }
                            }

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
                        }

                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(radius = blurRadius.dp),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            Overlay(myThoughtAddIsFocused && !mainOverlayIsVisible) {
                                myThoughtAddIsFocused = false
                            }

                            MyThoughtsAdd(
                                viewModel = viewModel,
                                innerPadding = innerPadding,
                                bookId = bookId,
                                handleAddThought = handleAddThought,
                                isFocused = myThoughtAddIsFocused,
                                handleFocus = { isFocused ->
                                    myThoughtAddIsFocused = isFocused
                                }
                            )

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

                        Overlay(mainOverlayIsVisible) {
                            deleteBookDialogIsVisible = false
                            isCoverShown = false
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

                        AnimatedVisibility(
                            visible = isCoverShown,
                            enter = fadeIn(),
                            exit = fadeOut(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(horizontal = 30.dp)
                                    .aspectRatio(0.66f)
                                    .clip(RoundedCornerShape(26.dp))
                                    .background(MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center,
                            ) {
                                if (book.coverUri != null) {
                                    CircularProgressIndicator()

                                    AsyncImage(
                                        model = book.coverUri,
                                        modifier = Modifier.fillMaxSize(),
                                        contentDescription = book.title,
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                        }

                        ShareCard(
                            book = book,
                            rates = rates ?: emptyList(),
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