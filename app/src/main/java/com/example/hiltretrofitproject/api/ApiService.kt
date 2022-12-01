package com.example.hiltretrofitproject.api

import com.example.hiltretrofitproject.model.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("movielist.json")
    suspend fun getMovies(): Response<MovieList>
}