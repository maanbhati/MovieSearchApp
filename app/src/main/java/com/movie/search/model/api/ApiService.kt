package com.movie.search.model.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("movies")
    suspend fun getMoviesResponse(): Response<MovieResponse>
}