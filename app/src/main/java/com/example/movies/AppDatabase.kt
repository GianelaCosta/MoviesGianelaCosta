package com.example.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.MovieDao

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}