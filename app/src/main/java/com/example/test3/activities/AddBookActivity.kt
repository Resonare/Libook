package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test3.R
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.add.Form
import com.example.test3.ui.theme.LibookTheme

class AddBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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

        setContent {
            val viewModel: BookViewModel = viewModel()

            LibookTheme {
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
                                    tryToAddBook(viewModel)
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



