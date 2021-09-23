package com.movie.search.model.api

import retrofit2.Response

interface ApiHelper {
    suspend fun getMoviesResponse(): Response<MovieResponse>
}