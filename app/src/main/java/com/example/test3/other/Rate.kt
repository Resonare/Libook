package com.example.test3.other

import com.example.test3.R
import java.io.Serializable

enum class RateType(val iconId: Int, val descriptionId: Int) {
    GENERAL(R.drawable.ic_star, R.string.rate_desc_general),
    CHARACTERS(R.drawable.ic_human, R.string.rate_desc_characters),
    EXPECTATIONS(R.drawable.ic_scale, R.string.rate_desc_expectations),
    PLOT(R.drawable.ic_feather, R.string.rate_desc_plot)
}

data class Rate(val type: RateType, val value: Int) : Serializable