package com.example.test3.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.test3.R
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.RateType
import com.example.test3.data.viewModels.BookViewModel
import com.example.test3.ui.components.CircleButton
import com.example.test3.ui.components.book.show.ShareCard
import com.example.test3.ui.components.rate.RateControl
import com.example.test3.ui.components.rate.RemainingRates
import com.example.test3.ui.theme.LibookTheme

class EditRatesActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bookId = intent.getStringExtra("bookId")

        if (bookId == null) {
            Toast.makeText(this, R.string.error_book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val handleGoBack = {
            val intent = Intent(this, ShowBookActivity::class.java)
            intent.putExtra("bookId", bookId)
            startActivity(intent)
        }

        val handleAddRate = { rateType: RateType, value: Int, viewModel: BookViewModel ->
            viewModel.addRate(bookId, rateType, value) {
                handleGoBack()
            }
        }

        val handleEditRate = { rate: Rate, newValue: Int, viewModel: BookViewModel ->
            viewModel.editRate(
                id = rate.id,
                bookId = rate.bookId,
                type = rate.type,
                value = newValue,
            ) {
                handleGoBack()
            }
        }

        val handleDeleteRate = { rate: Rate, viewModel: BookViewModel ->
            viewModel.deleteRate(rate.id) {
                handleGoBack()
            }
        }

        setContent {
            val viewModel: BookViewModel = viewModel()

            var isSharing by remember { mutableStateOf(false) }
            val handleShare = {
                isSharing = true
            }

            val bookFullTriple by viewModel.getBook(bookId).observeAsState()
            val book = if (bookFullTriple != null) bookFullTriple!!.first else null
            val rates = if (bookFullTriple != null) bookFullTriple!!.third else null

            if (rates == null) return@setContent

            var currentRateType by remember {
                mutableStateOf(
                    RateType.valueOf(
                        intent.getStringExtra("rateTypeName") ?: "GENERAL"
                    )
                )
            }

            val currentRate: Rate? = rates.find { rate ->
                rate.type.name == currentRateType.name
            }

            val listState = rememberLazyListState(
                initialFirstVisibleItemIndex = currentRate?.value ?: 0
            )

            val handleRateTypeChange = { rate: Rate?, newRateType: RateType ->
                currentRateType = newRateType
            }

            val context = LocalContext.current

            val savedTheme = context
                .getSharedPreferences("settings", MODE_PRIVATE)
                .getBoolean("dark_theme", isSystemInDarkTheme())

            var isDarkTheme by remember { mutableStateOf(savedTheme) }

            LibookTheme (
                darkTheme = isDarkTheme
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Box (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                        ) {
                            CircleButton(
                                painter = painterResource(R.drawable.ic_cancel),
                                onClick = {
                                    handleGoBack()
                                }
                            )
                        }

                        if (book != null) {
                            Column (
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column (
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.35f)
                                            .aspectRatio(0.66f)
                                            .clip(RoundedCornerShape(12.dp))
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

                                    Spacer(Modifier.height(10.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(0.32f),
                                        text = book.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.secondary,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                RateControl(
                                    listState = listState,
                                    currentRate = currentRate,
                                    currentRateType = currentRateType,
                                    handleAdd = { rateType: RateType, value: Int ->
                                        handleAddRate(rateType, value, viewModel)
                                    },
                                    handleEdit = { rate: Rate, newValue: Int ->
                                        handleEditRate(rate, newValue, viewModel)
                                    },
                                    handleDelete = { rate: Rate ->
                                        handleDeleteRate(rate, viewModel)
                                    },
                                    handleShare = handleShare,
                                    handleGoBack = handleGoBack,
                                )

                                RemainingRates(rates, currentRateType, handleRateTypeChange)
                            }
                        }
                    }

                    if (book != null) {
                        ShareCard(
                            book = book,
                            rates = rates,
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