package com.example.test3.data.entities

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test3.ui.theme.Black80
import com.example.test3.ui.theme.Green100
import com.example.test3.ui.theme.Red100
import java.util.UUID

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val description: String,
    var worldRate: Float?,
    val coverUri: String? = null,
    val isFavourite: Boolean = false,
) {
    val worldRateColor: Color
        get () =
            if (worldRate != null) {
                getWorldRateColor(worldRate!!)
            } else {
                Black80
            }

    companion object {
        fun getWorldRateColor(worldRate: Float): Color {
            return when(worldRate) {
                in 1f..4f -> Red100
                in 7f..10f -> Green100
                else -> Black80
            }
        }
    }
}