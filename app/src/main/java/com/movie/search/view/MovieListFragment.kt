package com.movie.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.movie.search.databinding.MovieListFragmentBinding
import com.movie.search.model.api.MovieResponse
import com.movie.search.viewmodel.ListViewModel
import com.movie.search.viewmodel.MovieListEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var binding: MovieListFragmentBinding? = null

    private lateinit var movieListViewModel: ListViewModel
    private val movieListAdapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding?.apply {
            movieList.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = movieListAdapter
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        movieListAdapter.filter.filter(query)
                        dismissKeyboard(searchView)
                        searchView.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    movieListAdapter.filter.filter(newText)
                    return true
                }
            })
        }

        bindMovieListViewModel()
        movieListViewModel.getMovieList()
    }


    private fun bindMovieListViewModel() =
        lifecycleScope.launchWhenCreated {
            movieListViewModel.movieResponse.collect { event ->
                when (event) {
                    is MovieListEvent.Success -> {
                        shouldShowProgressBar(false)
                        updateMovieList(event.movieResponse)
                    }
                    is MovieListEvent.Loading -> {
                        shouldShowProgressBar(true)
                    }
                    is MovieListEvent.Error -> {
                        shouldShowProgressBar(false)
                        binding?.apply {
                            Snackbar.make(movieList, "Something went wrong", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                    is MovieListEvent.Empty -> {
                        shouldShowProgressBar(false)
                    }
                }
            }
        }

    private fun shouldShowProgressBar(shouldShowProgress: Boolean) =
        binding?.apply {
            progressBar.visibility = if (shouldShowProgress) View.VISIBLE else View.GONE
        }

    private fun updateMovieList(movieResponse: MovieResponse) =
        binding?.apply {
            movieListAdapter.onAddMovieItem(movieResponse.movieList)
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
