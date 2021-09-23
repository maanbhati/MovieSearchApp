package com.movie.search.viewmodel

import com.movie.search.model.api.MovieResponse

sealed class MovieListEvent {
    class Success(val movieResponse: MovieResponse) : MovieListEvent()
    class Error(val message: String) : MovieListEvent()
    object Loading : MovieListEvent()
    object Empty : MovieListEvent()
}
