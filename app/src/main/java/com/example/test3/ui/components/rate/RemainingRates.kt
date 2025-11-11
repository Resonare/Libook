package com.example.test3.ui.components.rate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.RateType
import com.example.test3.ui.components.book.show.RateItem

@Composable
fun RemainingRates(rates: List<Rate>, currentRateType: RateType, handleRateTypeChange: (Rate?, RateType) -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentRateType != RateType.GENERAL) {
            val rate = rates.find { rate -> rate.type.name == RateType.GENERAL.name }

            RateItem (
                rate = rate,
                rateType = RateType.GENERAL
            ) {
                handleRateTypeChange(rate, RateType.GENERAL)
            }
        }

        if (currentRateType != RateType.CHARACTERS) {
            val rate = rates.find { rate -> rate.type.name == RateType.CHARACTERS.name }

            RateItem (
                rate = rate,
                rateType = RateType.CHARACTERS
            ) {
                handleRateTypeChange(rate, RateType.CHARACTERS)
            }
        }

        if (currentRateType != RateType.EXPECTATIONS) {
            val rate = rates.find { rate -> rate.type.name == RateType.EXPECTATIONS.name }

            RateItem (
                rate = rate,
                rateType = RateType.EXPECTATIONS
            ) {
                handleRateTypeChange(rate, RateType.EXPECTATIONS)
            }
        }

        if (currentRateType != RateType.PLOT) {
            val rate = rates.find { rate -> rate.type.name == RateType.PLOT.name }

            RateItem (
                rate = rate,
                rateType = RateType.PLOT
            ) {
                handleRateTypeChange(rate, RateType.PLOT)
            }
        }
    }
}