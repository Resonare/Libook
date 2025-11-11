package com.example.test3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.test3.R
import java.util.UUID

@Entity(
    tableName = "rates",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["book_id", "type"], unique = true)
    ]
)
data class Rate (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "book_id")
    val bookId: String,
    val value: Int = 0,
    val type: RateType
)

enum class RateType(val iconId: Int, val descriptionId: Int) {
    GENERAL(R.drawable.ic_star, R.string.rate_desc_general),
    CHARACTERS(R.drawable.ic_human, R.string.rate_desc_characters),
    EXPECTATIONS(R.drawable.ic_scale, R.string.rate_desc_expectations),
    PLOT(R.drawable.ic_feather, R.string.rate_desc_plot)
}