package com.example.test3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.example.test3.Rate
import com.example.test3.ui.theme.Black10
import com.example.test3.ui.theme.Black80
import com.example.test3.ui.theme.Green100
import com.example.test3.ui.theme.Red100

@Composable
fun RateItem(rate: Rate) {
    val color = when(rate.value) {
        in 1..4 -> Red100
        in 7..10 -> Green100
        else -> Black80
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(color)
                .padding(horizontal = 10.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (
                modifier = Modifier
                    .height(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(),
                    painter = painterResource(rate.type.iconId),
                    contentDescription = stringResource(rate.type.descriptionId),
                    contentScale = ContentScale.Fit,
                )
            }

            Spacer(Modifier.width(10.dp))

            Text(
                text = rate.value.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Black10
            )
        }

        Text(
            text = stringResource(rate.type.descriptionId),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
    }
}