package com.example.test3.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Thought

data class BookWithThoughts(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id",
    ) val thoughts: List<Thought>
)