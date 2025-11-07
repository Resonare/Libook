package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.data.entities.Book
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.other.FilterOption
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

            LibookTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Shelf(innerPadding, viewModel, handleShowBook, handleFilterSelect)

                        Footer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.15f)
                                .align(Alignment.BottomCenter),
                            onAddBookManually = handleAddBookManually,
                            onAddBookScan = handleAddBookScan
                        )
                    }
                }
            }
        }
    }
}

