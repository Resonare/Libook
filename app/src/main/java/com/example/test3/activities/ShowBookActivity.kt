package com.example.test3.activities

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.BookGeneralInfo
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

        setContent {
            val viewModel: BookViewModel = viewModel()

            val book by viewModel.getBook(bookId).observeAsState()

            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    if (book != null) {
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
                    }
                }
            }
        }
    }
}