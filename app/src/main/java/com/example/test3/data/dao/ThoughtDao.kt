package com.example.test3.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test3.data.entities.Thought

@Dao
interface ThoughtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(thought: Thought)

    @Query("DELETE FROM thoughts WHERE id = :id")
    suspend fun delete(id: String)
}