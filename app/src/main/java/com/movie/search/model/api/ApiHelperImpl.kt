package com.movie.search.model.api

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun getMoviesResponse(): Response<MovieResponse> =
        apiService.getMoviesResponse()
}

