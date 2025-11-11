package com.example.test3.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.Thought

data class BookFull(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id",
    ) val thoughts: List<Thought>,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id",
    ) val rates: List<Rate>
)