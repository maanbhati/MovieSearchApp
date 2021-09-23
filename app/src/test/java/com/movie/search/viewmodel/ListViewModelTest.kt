package com.movie.search.viewmodel

import com.movie.search.model.repository.MovieListRepository
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
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
    fun `verify livedata values changes on event`() = runBlockingTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            assertNotNull(listViewModel.movieResponse)
        }
    }
}