package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.other.FilterOption
import com.example.test3.ui.components.shelf.AddBookMethodSelection
import com.example.test3.ui.components.shelf.Footer
import com.example.test3.ui.components.shelf.Shelf
import com.example.test3.ui.theme.LibookTheme

class ShelfActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val handleShowBook = { book: Book ->
            val intent = Intent(this, ShowBookActivity::class.java)
            intent.putExtra("bookId", book.id)
            startActivity(intent)
        }

        val handleFilterSelect: (FilterOption) -> Unit = {}

        val handleAddBookScan = {

        }

        val handleAddBookManually = {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            var addMethodSelectionIsVisible by remember { mutableStateOf(false) }

            val blurRadius by animateFloatAsState(
                targetValue = if (addMethodSelectionIsVisible) 16f else 0f,
                animationSpec = tween(durationMillis = 500)
            )

            LibookTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (
                        modifier =
                            if (addMethodSelectionIsVisible)
                                Modifier.fillMaxSize().blur(radius = blurRadius.dp)
                            else
                                Modifier.fillMaxSize()
                    ) {
                        Shelf(innerPadding, viewModel, handleShowBook, handleFilterSelect)

                        Footer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.2f)
                                .align(Alignment.BottomCenter),
                            onAddMethodSelectionOpen = {
                                addMethodSelectionIsVisible = true
                            },
                            innerPadding = innerPadding
                        )
                    }

                    AnimatedVisibility(
                        visible = addMethodSelectionIsVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0, 0, 0, 153))
                                .clickable {
                                    addMethodSelectionIsVisible = false
                                },
                        )
                    }

                    AnimatedVisibility(
                        visible = addMethodSelectionIsVisible,
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
                            AddBookMethodSelection(
                                onAddBookManually = handleAddBookManually,
                                onAddBookScan = handleAddBookScan,
                                innerPadding = innerPadding
                            )
                        }
                    }
                }
            }
        }
    }
}