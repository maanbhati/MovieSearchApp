package com.movie.search.model.repository

import androidx.lifecycle.LiveData
import com.movie.search.model.api.Movie
import com.movie.search.model.api.MovieResponse
import retrofit2.Response

interface MovieListRepository {
    fun getAllMovies(): LiveData<List<Movie>>

    suspend fun getMoviesResponse(): Response<MovieResponse>

    suspend fun insertMovie(movie: List<Movie>): List<Long>

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteAllMovies()
}