package com.movie.search.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movie.search.model.api.Movie
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    class Callback @Inject constructor(
        private val database: Provider<MovieDatabase>
    ) : RoomDatabase.Callback()
}