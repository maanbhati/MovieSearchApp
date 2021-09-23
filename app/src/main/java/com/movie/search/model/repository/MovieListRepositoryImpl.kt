package com.movie.search.model.repository

import com.movie.search.model.api.ApiHelper
import com.movie.search.model.api.Movie
import com.movie.search.model.api.MovieResponse
import com.movie.search.model.room.MovieDao
import retrofit2.Response
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val movieDao: MovieDao
) : MovieListRepository {
    override suspend fun getMoviesResponse(): Response<MovieResponse> =
        apiHelper.getMoviesResponse()

    override fun getAllMovies() = movieDao.getMovieList()

    override suspend fun insertMovie(movie: List<Movie>) = movieDao.insert(movie)

    override suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    override suspend fun deleteAllMovies() = movieDao.deleteAllMovies()
}