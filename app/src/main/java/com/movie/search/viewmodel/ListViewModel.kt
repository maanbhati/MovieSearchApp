package com.movie.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movie.search.model.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _movieResponse = MutableStateFlow<MovieListEvent>(MovieListEvent.Empty)

    val movieResponse: StateFlow<MovieListEvent> = _movieResponse

    fun getMovieList() = viewModelScope.launch {
        _movieResponse.value = MovieListEvent.Loading
        movieListRepository.getMoviesResponse().let { response ->
            if (response.isSuccessful) {
                response.let { it ->
                    it.body()?.let {
                        _movieResponse.value = MovieListEvent.Success(it)
                        it.movieList?.let { movieList -> movieListRepository.insertMovie(movieList) }
                    }
                }
            } else {
                _movieResponse.value = MovieListEvent.Error(response.errorBody().toString())
            }
        }
    }
}