package com.example.test3.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test3.data.dao.BookDao
import com.example.test3.data.entities.Book

@Database(entities = [(Book::class)], version = 2)
abstract class LibookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: LibookDatabase? = null
        fun getInstance(context: Context): LibookDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LibookDatabase::class.java,
                        "Libook_database"
                    ).fallbackToDestructiveMigration(false).build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}