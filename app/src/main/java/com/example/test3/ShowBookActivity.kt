package com.example.test3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.test3.ui.theme.Black80
import com.example.test3.ui.theme.Green100
import com.example.test3.ui.theme.LibookTheme
import com.example.test3.ui.theme.Red100

class ShowBookActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val book = intent.getSerializableExtra("book") as? Book

        if (book == null) {
            Toast.makeText(this, R.string.error_book_not_found, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setContent {
            LibookTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box (modifier = Modifier.padding(innerPadding)) {
                        Column {
                            BookGeneralInfo(book)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun BookGeneralInfo(book: Book) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(0.66f)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painterResource(book.coverId),
                modifier = Modifier.fillMaxSize(),
                contentDescription = book.title,
                contentScale = ContentScale.FillWidth,
            )
        }

        Spacer(Modifier.height(20.dp))

        Column (
            modifier = Modifier.fillMaxWidth(0.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            Row() {
                Text(
                    modifier = Modifier,
                    text = "8.1",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
                    color = Green100,
                )

                Spacer(Modifier.width(5.dp))

                Text(
                    modifier = Modifier,
                    text = "Дж. К. Роулинг",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

enum class RateType(val iconId: Int, val descriptionId: Int) {
    GENERAL(R.drawable.ic_star, R.string.rate_desc_general),
    CHARACTERS(R.drawable.ic_human, R.string.rate_desc_characters),
    EXPECTATIONS(R.drawable.ic_scale, R.string.rate_desc_expectations),
    PLOT(R.drawable.ic_feather, R.string.rate_desc_plot)
}

data class Rate(val type: RateType, val value: Int)

@Composable
fun RateItem(rate: Rate) {
    val color = when(rate.value) {
        in 1..4 -> Red100
        in 7..10 -> Green100
        else -> Black80
    }

    Column {
        Row {
            Box(
//                modifier = Modifier
//                    .fillMaxWidth(0.4f)
//                    .aspectRatio(0.66f)
//                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    //                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(rate.type.iconId),
                    contentDescription = stringResource(rate.type.descriptionId),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}