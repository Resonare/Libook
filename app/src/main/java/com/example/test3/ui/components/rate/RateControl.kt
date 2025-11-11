package com.example.test3.ui.components.rate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test3.R
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.RateType
import com.example.test3.ui.theme.Black10

@Composable
fun RateControl(
    listState: LazyListState,
    currentRate: Rate?,
    currentRateType: RateType,
    handleAdd: (RateType, Int) -> Unit,
    handleEdit: (Rate, Int) -> Unit,
    handleDelete: (Rate) -> Unit,
    handleShare: () -> Unit,
    handleGoBack: () -> Unit,
) {
    val currentIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf null

            // Центр LazyRow (в пикселях)
            val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2

            // Находим item, в который попадает центр
            layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                kotlin.math.abs(itemCenter - viewportCenter)
            }?.index
        }
    }

    val submitText = when {
        currentIndex == 0 && currentRate == null -> stringResource(R.string.rate_control_do_nothing)
        currentIndex == 0 && currentRate != null -> stringResource(R.string.rate_control_delete)
        currentRate != null && currentRate.value != currentIndex -> stringResource(R.string.rate_control_edit)
        currentRate != null && currentRate.value == currentIndex -> stringResource(R.string.rate_control_share)
        else -> stringResource(R.string.rate_control_add)
    }

    val handleSubmit = {
        when {
            currentIndex == 0 && currentRate == null -> handleGoBack()
            currentIndex == 0 && currentRate != null -> handleDelete(currentRate)
            currentRate != null && currentRate.value != currentIndex -> handleEdit(currentRate, currentIndex ?: 0)
            currentRate != null && currentRate.value == currentIndex -> handleShare()
            else -> handleAdd(currentRateType, currentIndex ?: 0)
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(currentRateType.descriptionId),
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        RateCarousel(listState, currentIndex ?: 0)

        Spacer(Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 6.dp,
                        spread = 1.dp,
                        color = Color(0x40000000),
                    )
                )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    handleSubmit()
                }
                .padding(vertical = 18.dp, horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (currentRate != null && currentRate.value == currentIndex) {
                Image(
                    painter = painterResource(R.drawable.ic_share_big),
                    contentDescription = "Share"
                )

                Spacer(Modifier.width(14.dp))
            }

            Text(
                text = submitText,
                color = Black10,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}