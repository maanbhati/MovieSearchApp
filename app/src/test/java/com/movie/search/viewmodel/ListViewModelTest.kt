package com.movie.search.viewmodel

import com.movie.search.model.repository.MovieListRepository
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ListViewModelTest {

    @Mock
    private lateinit var movieListRepository: MovieListRepository

    private lateinit var listViewModel: ListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        listViewModel = ListViewModel(movieListRepository)
    }

    @Test
    fun `verify livedata values changes event is not null`() {
        assertNotNull(listViewModel.movieResponse)
    }
}