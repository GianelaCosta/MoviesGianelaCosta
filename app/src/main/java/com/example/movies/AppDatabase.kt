package com.example.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies.data.model.GenreConverter
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.ReviewEntity
import com.example.movies.domain.MovieDao

@Database(entities = [MovieEntity::class, ReviewEntity::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}