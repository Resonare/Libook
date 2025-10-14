package com.example.test3

import FilterBar
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.test3.ui.components.BooksGrid
import com.example.test3.ui.components.SearchBar
import com.example.test3.ui.components.Footer
import com.example.test3.ui.theme.LibookTheme

class ShelfActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val handleShowBook = { book: Book ->
            val intent = Intent(this, ShowBookActivity::class.java)
            intent.putExtra("book", book)
            startActivity(intent)
        }

        val handleFilterSelect: (FilterOption) -> Unit = {}

        setContent {
            LibookTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                SearchBar(
                                    hintText = stringResource(R.string.search_hint)
                                )

                                Spacer(Modifier.height(15.dp))

                                FilterBar(handleFilterSelect)

                                Spacer(Modifier.height(15.dp))

                                BooksGrid(handleShowBook)
                            }
                        }

                        Footer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.15f)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

