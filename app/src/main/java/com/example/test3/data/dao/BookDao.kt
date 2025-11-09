package com.example.test3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.test3.data.entities.Book
import com.example.test3.data.relations.BookWithThoughts

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): LiveData<List<Book>>

    @Transaction
    @Query("SELECT * FROM books WHERE id = :id")
    fun getOne(id: String): LiveData<BookWithThoughts?>

//    @Query("SELECT * FROM books WHERE id = :id")
//    fun getOne(id: String): LiveData<Book?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun delete(id: String)
}