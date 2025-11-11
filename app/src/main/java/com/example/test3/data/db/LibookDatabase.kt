package com.example.test3.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.test3.data.dao.BookDao
import com.example.test3.data.dao.RateDao
import com.example.test3.data.dao.ThoughtDao
import com.example.test3.data.entities.Book
import com.example.test3.data.entities.Rate
import com.example.test3.data.entities.Thought
import com.example.test3.other.ColorConverters
import com.example.test3.other.RateTypeConverter

@Database(
    entities = [Book::class, Thought::class, Rate::class],
    version = 7
)
@TypeConverters(ColorConverters::class, RateTypeConverter::class)
abstract class LibookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun thoughtDao(): ThoughtDao
    abstract fun rateDao(): RateDao

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