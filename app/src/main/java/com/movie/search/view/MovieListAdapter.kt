package com.movie.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.movie.search.databinding.ItemMoviesListBinding
import com.movie.search.model.api.Movie
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class MovieListAdapter :
    RecyclerView.Adapter<MovieListAdapter.MoviesItemViewHolder>(), Filterable {

    private var moviesFilterList = emptyList<Movie>()
    private var movieItems = emptyList<Movie>()

    fun onAddMovieItem(items: List<Movie>?) {
        items?.let {
            movieItems = it
            moviesFilterList = movieItems
            notifyItemChanged(moviesFilterList.size - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MoviesItemViewHolder =
        MoviesItemViewHolder(
            ItemMoviesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = moviesFilterList.size

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) =
        holder.bind(moviesFilterList[position])

    inner class MoviesItemViewHolder(binding: ItemMoviesListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val moviePoster = binding.moviePoster
        private val movieGenre = binding.movieGenre
        private val movieTitle = binding.movieTitle
        private val movieReleaseYear = binding.movieReleaseYear

        fun bind(movieItem: Movie) {
            moviePoster.loadImage(movieItem.poster)
            movieGenre.text = movieItem.genre
            movieTitle.text = movieItem.title
            movieReleaseYear.text = movieItem.year
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    moviesFilterList = movieItems
                } else {
                    val resultList = ArrayList<Movie>()
                    for (movie in movieItems) {
                        movie.genre?.let {
                            if (it.lowercase()
                                    .startsWith(constraint.toString().lowercase(), true)
                            ) {
                                resultList.add(movie)
                            }
                        }
                        movie.title?.let {
                            if (it.lowercase()
                                    .startsWith(constraint.toString().lowercase(), true)
                            ) {
                                resultList.add(movie)
                            }
                        }
                    }
                    moviesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFilterList = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }
}