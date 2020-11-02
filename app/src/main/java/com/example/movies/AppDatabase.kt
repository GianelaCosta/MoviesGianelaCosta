package com.example.movies

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.MovieDao

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies_table").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}