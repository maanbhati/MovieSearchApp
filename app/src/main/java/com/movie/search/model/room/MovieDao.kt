package com.movie.search.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movie.search.model.api.Movie
import org.jetbrains.annotations.NotNull

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getMovieList(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(@NotNull movie: List<Movie>): List<Long>

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()
}