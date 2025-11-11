package com.example.test3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test3.data.entities.Rate

@Dao
interface RateDao {
    @Query("SELECT * FROM rates WHERE id = :id")
    fun getOne(id: String): LiveData<Rate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: Rate)

    @Update
    suspend fun update(rate: Rate)

    @Query("DELETE FROM rates WHERE id = :id")
    suspend fun delete(id: String)
}